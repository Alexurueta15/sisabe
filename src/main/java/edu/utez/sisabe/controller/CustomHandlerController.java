package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.ListErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class CustomHandlerController {

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage usernameNotFoundException() {
        return new ErrorMessage("Usuario del token no encontrado");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage accessDenied(){
        return new ErrorMessage("Forbidden");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ListErrorMessage methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        return new ListErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Los argumentos no son válidos",
                errorMessages
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage notBodyException() {
        return new ErrorMessage("La solicitud no tiene cuerpo o no es de tipo application/json");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ErrorMessage(
                ex.getCause() == null ? "El servidor no añadió detalles" :
                        ex.getCause().toString().split(":", 2)[1]);
    }
}