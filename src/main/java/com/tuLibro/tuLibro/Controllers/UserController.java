package com.tuLibro.tuLibro.Controllers;

import com.tuLibro.tuLibro.DTOs.UserDTO;
import com.tuLibro.tuLibro.Entities.UserEntity;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserAlreadyExistsException;
import com.tuLibro.tuLibro.Security.Jwt.JwtUtils;
import com.tuLibro.tuLibro.DTOs.LoginRequest;
import com.tuLibro.tuLibro.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userdto", new UserDTO());
        return "register";
    }
    @PostMapping("/registerUser")
    public String createUser(@ModelAttribute("userdto") @Valid UserDTO user) throws UserAlreadyExistsException {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user",new LoginRequest());
        return"index";
    }

    @PostMapping("/userLogin")
    public String login(@ModelAttribute("user")@Valid LoginRequest loginRequest, HttpServletResponse response){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= jwtUtils.generateToken(loginRequest.getUsername());
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        UserEntity userAuthenticaded= userService.getUserByUsername(loginRequest.getUsername());

        if(userAuthenticaded.getRole().stream().anyMatch(role->"ADMIN".equals(role.getRol().name()))){
            return "redirect:/libros";
        }else{
            return "templateUsuario";
        }
    }



    @GetMapping("/logoutt")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies2 = request.getCookies();
        for (Cookie cookie : cookies2) {
            if (cookie.getName().equals("jwt")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/login";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userService.deleteUser(Long.parseLong(id));
        return "el id "+id+" fue eliminado";
    }
    @DeleteMapping("deleteAllUsers")
    public String deleteAllUsers(){
        userService.deleteAllUsers();
        return "Todos los users fueron eliminados correctamente";
    }
}
