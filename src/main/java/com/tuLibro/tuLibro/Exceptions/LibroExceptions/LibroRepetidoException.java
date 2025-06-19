package com.tuLibro.tuLibro.Exceptions.LibroExceptions;

public class LibroRepetidoException extends Exception{
    public LibroRepetidoException(String nombre_libro,String nombre_autor,String apellido_autor) {
        super("el libro "+nombre_libro+" del autor "+nombre_autor+" "+apellido_autor+" ya esta registrado");
    }
}
