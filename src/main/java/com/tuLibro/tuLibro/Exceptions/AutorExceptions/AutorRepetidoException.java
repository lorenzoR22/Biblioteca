package com.tuLibro.tuLibro.Exceptions.AutorExceptions;

public class AutorRepetidoException extends Exception{
    public AutorRepetidoException(String nombre_autor,String apellido_autor) {
        super("El autor "+nombre_autor+ " "+apellido_autor+" ya esta registrado");
    }
}
