package com.tuLibro.tuLibro.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private Long id;

    @NotNull(message = "el nombre no puede ser nulo")
    private String nombre;
    @NotNull(message = "el apellido no puede ser nulo")
    private String apellido;
    @NotNull(message = "el genero no puede ser nulo")
    private String genero;



}
