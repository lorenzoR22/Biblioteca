package com.tuLibro.tuLibro.Exceptions.AutorExceptions;

public class AutorNoEncontradoException extends Exception{
    public AutorNoEncontradoException(Long id_autor) {
        super("No se encontro el autor con el id: "+id_autor);
    }
}
