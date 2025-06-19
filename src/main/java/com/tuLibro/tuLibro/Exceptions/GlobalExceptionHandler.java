package com.tuLibro.tuLibro.Exceptions;

import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroRepetidoException;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserRepetidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibroRepetidoException.class)
    public ResponseEntity<ErrorResponse>handleLibroRepetidoException(LibroRepetidoException e){
        ErrorResponse error=new ErrorResponse("LIBRO_REPETIDO",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AutorRepetidoException.class)
    public ResponseEntity<ErrorResponse>handleAutorRepetidoException(AutorRepetidoException e){
        ErrorResponse error=new ErrorResponse("AUTOR_REPETIDO",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserRepetidoException.class)
    public ResponseEntity<ErrorResponse>UserAlreadyExistsException(UserRepetidoException e){
        ErrorResponse error=new ErrorResponse("USER_REPETIDO",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>handleGenericException(Exception e){
        ErrorResponse error=new ErrorResponse("INTERNAL_SERVER_ERROR",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AutorNoEncontradoException.class)
    public ResponseEntity<ErrorResponse>handleAutorNoEncontradoException(AutorNoEncontradoException e){
        ErrorResponse error=new ErrorResponse("AUTOR_NO_ENCONTRADO",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LibroNoEncontradoException.class)
    public ResponseEntity<ErrorResponse>handleLibroNoEncontradoException(AutorNoEncontradoException e){
        ErrorResponse error=new ErrorResponse("LIBRO_NO_ENCONTRADO",e.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

}
