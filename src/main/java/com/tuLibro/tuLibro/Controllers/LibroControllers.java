package com.tuLibro.tuLibro.Controllers;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.DTOs.LibroDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroRepetidoException;
import com.tuLibro.tuLibro.Repositories.LibroRepository;
import com.tuLibro.tuLibro.Services.AutorService;
import com.tuLibro.tuLibro.Services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class LibroControllers {
    
    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @GetMapping("/libros")
    public String getAllLibros(Model model){
        model.addAttribute("libros",libroService.getAllLibros());
        return "libros";
    }

    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id){
        return libroService.getLibroById(id);
    }

    @GetMapping("/showFormAddLibro")
    public String showFormAddLibro(Model model){
        model.addAttribute("newLibro",new LibroDTO());
        model.addAttribute("autores",autorService.getAllAutores());
        return "addLibro";
    }

    @PostMapping("/addLibro")
    public String addLibro(@ModelAttribute("newLibro")@Valid LibroDTO libro){
        try {
            Autor autor=autorService.getAutorById(libro.getAutor().getId());
            libro.setAutor(new AutorDTO(
                    autor.getId(),
                    autor.getNombre(),
                    autor.getApellido(),
                    autor.getGenero()
            ));
            libroService.saveLibro(libro);
        }catch (LibroRepetidoException | AutorNoEncontradoException e){
            return "redirect:/addLibro";
        }
        return"redirect:/libros";
    }
    @GetMapping("/showFormUpdateLibro/{id}")
    public String showFormUpdate(@PathVariable ("id")Long id,Model model){
        Libro libroExistente=libroService.getLibroById(id);
        LibroDTO libro=new LibroDTO(id,
                libroExistente.getNombre(),
                new AutorDTO(
                        libroExistente.getAutor().getId(),
                        libroExistente.getAutor().getNombre(),
                        libroExistente.getAutor().getApellido(),
                        libroExistente.getAutor().getGenero()
                ),
                libroExistente.getPrecio()
        );
        model.addAttribute("libro",libro);
        model.addAttribute("autores",autorService.getAllAutores());
        return "update_libro";
    }
    @PostMapping("/libroUpdate")
    public String actualizarLibro(@ModelAttribute("libro") @Valid LibroDTO libro) throws LibroRepetidoException, AutorNoEncontradoException {
        libroService.actualizarLibro(libro);
        return "redirect:/libros";
    }
    @GetMapping("/delete/{id}")
    public String borrarLibro(@PathVariable("id") Long id){
        libroService.borrarLibro(id);
        return "redirect:/libros";
    }
    @GetMapping("/delete/libro/{id}")
    public String borrarLibroDesdeAutor(@PathVariable("id") Long id){
        libroService.borrarLibro(id);
        return "redirect:/autores";
    }

}
