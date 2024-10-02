package com.tuLibro.tuLibro.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
    private Long id;

    @NotNull(message = "el nombre no puede ser nulo")
    @Size(min=1, max=100,message="el nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @NotNull(message = "el autor no puede ser nulo")
    private AutorDTO autor;

    @NotNull(message = "el precio no puede ser nulo")
    @Min(value = 0,message = "El precio no puede ser negativo")
    private Double precio;

}
