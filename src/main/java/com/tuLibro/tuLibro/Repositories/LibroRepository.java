package com.tuLibro.tuLibro.Repositories;

import com.tuLibro.tuLibro.Entities.Autor;
import com.tuLibro.tuLibro.Entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    boolean existsByNombreAndAutor(String nombre,Autor autor);
    Optional<List<Libro>> findByAutorId(Long autorId);
}
