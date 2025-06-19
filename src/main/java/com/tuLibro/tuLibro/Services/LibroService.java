package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.DTOs.LibroDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroRepetidoException;
import com.tuLibro.tuLibro.Repositories.AutorRepository;
import com.tuLibro.tuLibro.Repositories.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public void saveLibro(LibroDTO libro) throws LibroRepetidoException, AutorNoEncontradoException {
        Libro newLibro=new Libro();
        if (libro.getAutor().getId() != null) {
            Autor autorExistente = autorRepository.findById(libro.getAutor().getId())
                    .orElseThrow(() -> new AutorNoEncontradoException(libro.getAutor().getId()));

            newLibro.setAutor(autorExistente);
        } else {
            Autor autorSaved =new Autor(
                    libro.getAutor().getNombre(),
                    libro.getAutor().getApellido(),
                    libro.getAutor().getGenero()
            );
            if (libroRepository.existsByNombreAndAutor(libro.getNombre(),autorSaved)) {
                throw new LibroRepetidoException(libro.getNombre(),autorSaved.getNombre(),autorSaved.getApellido());
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
        return LibrostoDTO(libros);
    }

    public LibroDTO getLibroById(Long id) throws LibroNoEncontradoException {
         Libro libro=libroRepository.findById(id)
                .orElseThrow(()->new LibroNoEncontradoException(id));
         return libroToDTO(libro);
    }

    public void actualizarLibro(LibroDTO libro) throws AutorNoEncontradoException, LibroNoEncontradoException {
        Autor autor=autorRepository.findById(libro.getAutor().getId())
                .orElseThrow(()->new AutorNoEncontradoException(libro.getAutor().getId()));

        Libro libroExistente=libroRepository.findById(libro.getId())
                .orElseThrow(()->new LibroNoEncontradoException(libro.getId()));

        libroExistente.setAutor(autor);
        libroExistente.setNombre(libro.getNombre());
        libroExistente.setPrecio(libro.getPrecio());

        libroRepository.save(libroExistente);
    }

    public void borrarLibro( Long id) throws LibroNoEncontradoException {
        Libro libroAborrar=libroRepository.findById(id).
                orElseThrow(()->new LibroNoEncontradoException(id));
        libroRepository.delete(libroAborrar);
    }

    public List<LibroDTO> LibrostoDTO(List<Libro>libros){
        return libros.stream()
                .map(this::libroToDTO)
                .collect(Collectors.toList());
    }
    public LibroDTO libroToDTO(Libro libro){
        return new LibroDTO(
                libro.getId(),
                libro.getNombre(),
                new AutorDTO(
                        libro.getAutor().getId(),
                        libro.getAutor().getNombre(),
                        libro.getAutor().getApellido(),
                        libro.getAutor().getGenero()),
                libro.getPrecio()
        );
    }
}
