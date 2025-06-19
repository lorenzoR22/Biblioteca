package com.tuLibro.tuLibro.Exceptions.UserExceptions;

public class UsernameNoEncontradoException extends Exception{
    public UsernameNoEncontradoException() {
    }

    public UsernameNoEncontradoException(String username) {
        super("No se encontro user con el username: "+username);
    }
}
