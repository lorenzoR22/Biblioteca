package com.tuLibro.tuLibro.Exceptions.LibroExceptions;

public class LibroNoEncontradoException extends Exception{
    public LibroNoEncontradoException() {
    }

    public LibroNoEncontradoException(Long id_libro) {
        super("No se encontro el libro con el id: "+id_libro);
    }
}
