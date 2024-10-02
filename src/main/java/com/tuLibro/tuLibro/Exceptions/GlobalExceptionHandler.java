package com.tuLibro.tuLibro.Exceptions;

import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorEmptyException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorNoEncontradoException;
import com.tuLibro.tuLibro.Exceptions.AutorExceptions.AutorRepetidoException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroEmptyException;
import com.tuLibro.tuLibro.Exceptions.LibroExceptions.LibroRepetidoException;
import com.tuLibro.tuLibro.Exceptions.UserExceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibroRepetidoException.class)
    public ResponseEntity<String>handleLibroRepetidoException(LibroRepetidoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Libro repetido:"+e.getMessage());
    }
    @ExceptionHandler(AutorRepetidoException.class)
    public ResponseEntity<String>handleAutorRepetidoException(AutorRepetidoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor ya registrado. "+e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>handleGenericException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado:"+e.getMessage());
    }
    @ExceptionHandler(AutorNoEncontradoException.class)
    public ResponseEntity<String>handleAutorNoEncontradoException(AutorNoEncontradoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor no encontrado:"+e.getMessage());
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String>UserAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error username:"+e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AutorEmptyException.class)
    public ResponseEntity<String>handleAutorEmptyException(AutorEmptyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos del autor:"+e.getMessage());
    }
    @ExceptionHandler(LibroEmptyException.class)
    public ResponseEntity<String>hadleLibroEmptyException(LibroEmptyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos del libro:"+e.getMessage());
    }
}
