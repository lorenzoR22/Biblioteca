package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.DTOs.LibroDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroRepetidoException;
import com.tuLibro.tuLibro.Repositories.AutorRepository;
import com.tuLibro.tuLibro.Repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorService autorService;

    public void saveLibro(LibroDTO libro) throws LibroRepetidoException, AutorNoEncontradoException {
        Libro newLibro=new Libro();
        if (libro.getAutor().getId() != null) {
            Autor autorExistente = autorRepository.findById(libro.getAutor().getId())
                    .orElseThrow(() -> new AutorNoEncontradoException("No se encontró el autor"));
            newLibro.setAutor(autorExistente);
        } else {
            Autor autorSaved =new Autor(
                    libro.getAutor().getNombre(),
                    libro.getAutor().getApellido(),
                    libro.getAutor().getGenero()
            );
            if (libroRepository.existsByNombreAndAutor(libro.getNombre(),autorSaved)) {
                throw new LibroRepetidoException("El libro con el nombre " +libro.getNombre()+
                        " y con el autor "+autorSaved.getNombre()+" ya existe.");
            }
            autorRepository.save(autorSaved);
            newLibro.setAutor(autorSaved);
        }
        newLibro.setNombre(libro.getNombre());
        newLibro.setPrecio(libro.getPrecio());
        libroRepository.save(newLibro);
    }

    public List<LibroDTO>getAllLibros(){
        List<Libro>libros=libroRepository.findAll();
        return libros.stream()
                .map(libro->new LibroDTO(
                        libro.getId(),
                        libro.getNombre(),
                        new AutorDTO(
                                libro.getAutor().getId(),
                                libro.getAutor().getNombre(),
                                libro.getAutor().getApellido(),
                                libro.getAutor().getGenero()),
                        libro.getPrecio()
                ))
                .collect(Collectors.toList());
    }
    public Libro getLibroById(Long id){
        return libroRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro el ID:"+id));
    }
    public void actualizarLibro(LibroDTO libro) throws  AutorNoEncontradoException {
        Autor autor=autorRepository.findById(libro.getAutor().getId())
                .orElseThrow(()->new AutorNoEncontradoException("no se encontro el autor"));

        Libro libroExistente=libroRepository.findById(libro.getId())
                .orElseThrow(()->new RuntimeException("no se encontro el ID:"+libro.getId()));

        libroExistente.setAutor(autor);
        libroExistente.setNombre(libro.getNombre());
        libroExistente.setPrecio(libro.getPrecio());

        libroRepository.save(libroExistente);
    }

    public String borrarLibro( Long id){
        Libro libroAborrar=libroRepository.findById(id).
                orElseThrow(()->new RuntimeException("no se encontro el ID:"+id));
        libroRepository.delete(libroAborrar);
        return "El libro con el id "+id+" ha sido borrado con exito";
    }
}
