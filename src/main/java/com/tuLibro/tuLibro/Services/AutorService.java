package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroNoEncontradoException;
import com.tuLibro.tuLibro.Repositories.AutorRepository;
import com.tuLibro.tuLibro.Repositories.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public void saveAutor(AutorDTO autorDTO)throws AutorRepetidoException{
       if(autorRepository.existsByNombreAndApellido(autorDTO.getNombre(),autorDTO.getNombre())){
           throw new AutorRepetidoException(autorDTO.getNombre(),autorDTO.getApellido());
       }
       Autor autorNew=new Autor(autorDTO.getNombre(),
               autorDTO.getApellido(),
               autorDTO.getGenero());

       autorRepository.save(autorNew);
    }

    public List<AutorDTO> getAllAutores(){
        List<Autor>autores=autorRepository.findAll();
        return autoresToDTO(autores);
    }

    public AutorDTO getLibroById(Long id) throws AutorNoEncontradoException {
        Autor autor= autorRepository.findById(id)
                .orElseThrow(()->new AutorNoEncontradoException(id));
        return autorToDTO(autor);
    }

    public Autor getAutorById(Long id) throws AutorNoEncontradoException {
       return autorRepository.findById(id)
               .orElseThrow(()->new AutorNoEncontradoException(id));
    }

    public List<Libro> getAllLibrosAutor(Long id) throws AutorNoEncontradoException {
         return libroRepository.findByAutorId(id)
                 .orElseThrow(()->new AutorNoEncontradoException(id));
    }

    public void actualizarAutor(AutorDTO autor) throws AutorRepetidoException {
        Autor autorExistente=autorRepository.findById(autor.getId())
                .orElseThrow(()->new AutorRepetidoException(autor.getNombre(),autor.getApellido()));

        autorExistente.setApellido(autor.getApellido());
        autorExistente.setNombre(autor.getNombre());
        autorExistente.setGenero(autor.getGenero());
        autorRepository.save(autorExistente);
    }

    public void borrarAutor(Long id) throws AutorNoEncontradoException {
        Autor autorAborrar=autorRepository.findById(id).
                orElseThrow(()->new AutorNoEncontradoException(id));
        autorRepository.delete(autorAborrar);
    }
    public AutorDTO autorToDTO(Autor autor){
        return new AutorDTO(
                autor.getId(),
                autor.getNombre(),
                autor.getApellido(),
                autor.getGenero()
        );
    }

    public List<AutorDTO>autoresToDTO(List<Autor>autores){
        return autores.stream()
                .map(this::autorToDTO)
                .collect(Collectors.toList());
    }
}
