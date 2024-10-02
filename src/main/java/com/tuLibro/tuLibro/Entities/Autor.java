package com.tuLibro.tuLibro.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "el nombre no puede ser nulo")
    @Size(min=1,max = 30,message = "el nombre debe tener entre 1 y 30 caracteres")
    private String nombre;

    @NotNull(message = "el apellido no puede ser nulo")
    @Size(min=1,max = 30,message = "el apellido debe tener entre 1 y 30 caracteres")
    private String apellido;

    @NotNull(message = "el genero no puede ser nulo")
    @Size(min=1,max = 30,message = "el genero debe tener entre 1 y 30 caracteres")
    private String genero;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Libro> libros;

    public Autor(String nombre, String apellido, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.libros=new ArrayList<>();
    }
    public Autor(Long id,String nombre, String apellido, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.libros=new ArrayList<>();
    }
}
