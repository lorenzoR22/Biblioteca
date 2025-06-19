package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.UserDTO;
import com.tuLibro.tuLibro.Entities.ERole;
import com.tuLibro.tuLibro.Entities.RoleEntity;
import com.tuLibro.tuLibro.Entities.UserEntity;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UsernameNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserRepetidoException;
import com.tuLibro.tuLibro.Repositories.RoleRepository;
import com.tuLibro.tuLibro.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void saveUser(UserDTO userDTO) throws UserRepetidoException {
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new UserRepetidoException(userDTO.getUsername());
        }
        Set<RoleEntity> roleEntities =toRoleEntity(userDTO);

        UserEntity userEntity2 =new UserEntity(userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(), roleEntities);
        userEntity2.setPassword(passwordEncoder.encode(userEntity2.getPassword()));

        userRepository.save(userEntity2);
    }

    public UserEntity getUserByUsername(String username) throws UsernameNoEncontradoException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNoEncontradoException(username)
        );
    }

    public void deleteUser(Long id) throws UserNoEncontradoException {
        userRepository.findById(id)
                .orElseThrow(()->new UserNoEncontradoException(id));
        userRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

    public Set<RoleEntity>toRoleEntity(UserDTO userDTO){
        return userDTO.getRoles().stream()
                .map(role-> roleRepository.findByRol(ERole.valueOf(role))
                        .orElseGet(()->{
                            RoleEntity newRoleEntity =new RoleEntity(ERole.valueOf(role));
                            return roleRepository.save(newRoleEntity);
                        }))
                .collect(Collectors.toSet());
    }
}
