package com.andres.curso.springboot.error.springboot_error.controllers;

import java.util.Date;
import java.util.Map;

import com.andres.curso.springboot.error.springboot_error.exceptions.UserNotFoundException;
import com.andres.curso.springboot.error.springboot_error.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class HandlerExceptionController {

    //AQUI VAN LOS METODOS PARA MANEJAR LAS EXCEPCIONES
    // Si hay mas excepciones en la llave se colocan con comas
    @ExceptionHandler({ArithmeticException.class})
    public ResponseEntity<Error> divisionByZero(Exception e){
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Error de división por cero"); 
        error.setMensaje(e.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        //return ResponseEntity.internalServerError().body(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
    } //calma por favor con las sugerencias si? :v
    
    // error 404 del usuario no encontrado Not found no se encontro la pagina o el usuario
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Error> notFound404(NoHandlerFoundException e){
        Error error = new Error();
        error.setDate(new Date());
        error.setError("Api Rest no encontrado"); 
        error.setMensaje(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value()); // not found es un enumerador constante que tiene el valor 404
        // existe ResponseEntity.notFound().build() pero no nos sirve porque no podemos enviar el body
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    // nueva forma de devolver la excepcion
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> numberFormatException(NumberFormatException e){
        return Map.of(
            "date", new Date(),
            "error", "Error de formato de número",
            "mensaje", e.getMessage(),
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    @ExceptionHandler({
        NullPointerException.class,
        HttpMessageNotWritableException.class,
        UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> userNotFoundException(Exception e){
        return Map.of(
            "date", new Date(),
            "error", "Error, el usuario o role no existe",
            "mensaje", e.getMessage(), // vaya myestra mi mensaje personalizado con la clase UserNotFoundException
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }


}
