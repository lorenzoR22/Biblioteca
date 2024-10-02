package com.tuLibro.tuLibro.DTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Email
    private String email;

    private Set<String> roles;

    public UserDTO(String username, String password,String mail) {
        this.username = username;
        this.password = password;
        this.email =mail;
        this.roles = new HashSet<>();
    }

}
