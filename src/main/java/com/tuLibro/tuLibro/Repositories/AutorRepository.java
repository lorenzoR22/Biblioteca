package com.tuLibro.tuLibro.Repositories;

import com.tuLibro.tuLibro.Entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {
    boolean existsByNombreAndApellido(String nombre,String apellido);
    Autor findByNombreAndApellido(String nombre,String apellido);
    Autor findAutorById(Long id);
}

