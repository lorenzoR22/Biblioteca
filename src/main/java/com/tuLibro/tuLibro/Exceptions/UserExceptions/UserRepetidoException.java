package com.tuLibro.tuLibro.Exceptions.UserExceptions;

public class UserRepetidoException extends Exception{
    public UserRepetidoException(String username) {
        super("El username: "+username+" ya esta en uso");
    }
}
