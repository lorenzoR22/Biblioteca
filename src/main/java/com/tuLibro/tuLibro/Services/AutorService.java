package com.tuLibro.tuLibro.Services;

import com.tuLibro.tuLibro.DTOs.AutorDTO;
import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Repositories.AutorRepository;
import com.tuLibro.tuLibro.Repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

    public Autor saveAutor(AutorDTO autorDTO)throws AutorRepetidoException{
       if(autorRepository.existsByNombreAndApellido(autorDTO.getNombre(),autorDTO.getNombre())){
           throw new AutorRepetidoException("Autor ya registrado");
       }
       Autor autorNew=new Autor(autorDTO.getNombre(),
               autorDTO.getApellido(),
               autorDTO.getGenero());
       return autorRepository.save(autorNew);
    }

    public List<AutorDTO> getAllAutores(){
        List<Autor>autores=autorRepository.findAll();
        return autores.stream()
                .map(autor->new AutorDTO(
                        autor.getId(),
                        autor.getNombre(),
                        autor.getApellido(),
                        autor.getGenero()
                ))
                .collect(Collectors.toList());
    }

    public AutorDTO getLibroById(Long id) throws AutorNoEncontradoException {
        Autor autor= autorRepository.findById(id)
                .orElseThrow(()->new AutorNoEncontradoException("No se encontro el autor"));
        return new AutorDTO(
                autor.getId(),
                autor.getNombre(),
                autor.getApellido(),
                autor.getGenero()
        );
    }

    public Autor getAutorById(Long id){
       return autorRepository.findAutorById(id);
    }

    public List<Libro> getAllLibrosAutor(Long id){
         return libroRepository.findByAutorId(id);
    }

    public Autor actualizarAutor(AutorDTO autor) throws AutorRepetidoException {
        Autor autorExistente=autorRepository.findById(autor.getId())
                .orElseThrow(()->new AutorRepetidoException("Autor no registrado."));

        autorExistente.setApellido(autor.getApellido());
        autorExistente.setNombre(autor.getNombre());
        autorExistente.setGenero(autor.getGenero());
        return autorRepository.save(autorExistente);
    }

    public String borrarAutor( Long id) throws AutorNoEncontradoException {
        Autor autorAborrar=autorRepository.findById(id).
                orElseThrow(()->new AutorNoEncontradoException("no se encontro el autor"));
        autorRepository.delete(autorAborrar);
        return "El autor con el id "+id+" ha sido borrado con exito";
    }
}
