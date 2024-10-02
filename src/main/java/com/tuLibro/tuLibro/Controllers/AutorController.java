package com.tuLibro.tuLibro.Controllers;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Services.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AutorController {
    @Autowired
    private AutorService autorService;

    @GetMapping("/autores")
    public String getAllAutores(Model model){
        model.addAttribute("autores",autorService.getAllAutores());
        return "autores";
    }

    @GetMapping("/librosAutor/{id}")
    public String getAllLibrosAutor(@PathVariable("id") Long id,Model model){
        model.addAttribute("librosAutor",autorService.getAllLibrosAutor(id));
        return "/librosAutor";
    }

    @GetMapping("/showNewAutorForm")
    public String showNewAutorForm(Model model) {
        Autor autor = new Autor();
        model.addAttribute("Autor", autor);
        return "addAutor";
    }

    @PostMapping("/addAutor")
    public String addAutor(@ModelAttribute("Autor") @Valid AutorDTO autorDTO) throws AutorRepetidoException {
        autorService.saveAutor(autorDTO);
        return "redirect:/autores";
    }
    @GetMapping("/showFormUpdateAutor/{id}")
    public String showFormUpdate(@PathVariable("id") Long id, Model model) throws AutorNoEncontradoException {
        AutorDTO savedAutor=autorService.getLibroById(id);
        model.addAttribute("autor",savedAutor);
        return "update_autor";
    }
    @PostMapping("/updateAutor")
    public String actualizarAutorLibro(@Valid AutorDTO autor) throws AutorRepetidoException {
        autorService.actualizarAutor(autor);
        return "redirect:/autores";
    }

    @GetMapping("/deleteAutor/{id}")
    public String borrarAutor(@PathVariable("id") Long id) throws AutorNoEncontradoException {
        autorService.borrarAutor(id);
        return "redirect:/autores";
    }

}
