package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.UserDTO;
import com.tuLibro.tuLibro.Entities.ERole;
import com.tuLibro.tuLibro.Entities.RoleEntity;
import com.tuLibro.tuLibro.Entities.UserEntity;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserAlreadyExistsException;
import com.tuLibro.tuLibro.Repositories.RoleRepository;
import com.tuLibro.tuLibro.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new UserAlreadyExistsException("Este username ya existe");
        }
        Set<RoleEntity> roleEntities =userDTO.getRoles().stream()
                .map(role-> roleRepository.findByRol(ERole.valueOf(role))
                        .orElseGet(()->{
                            RoleEntity newRoleEntity =new RoleEntity(ERole.valueOf(role));
                            return roleRepository.save(newRoleEntity);
                        }))
                .collect(Collectors.toSet());

        UserEntity userEntity2 =new UserEntity(userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(), roleEntities);

        userEntity2.setPassword(passwordEncoder.encode(userEntity2.getPassword()));
        return userRepository.save(userEntity2);
    }

    public void deleteUser(Long id){
        userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("No se encontro el id"));
        userRepository.deleteById(id);
    }

    public UserEntity getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("no se encontro el usuario")
        );
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
