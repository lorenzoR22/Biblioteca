package com.tuLibro.tuLibro.Exceptions.UserExceptions;

public class UserNoEncontradoException extends Exception{
    public UserNoEncontradoException() {
    }

    public UserNoEncontradoException(Long id_user) {
        super("No se encontro user con el id: ´"+id_user);
    }
}
