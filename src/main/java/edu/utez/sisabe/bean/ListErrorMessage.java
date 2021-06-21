package edu.utez.sisabe.bean;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
public class ListErrorMessage {
    private final int statusCode = HttpStatus.BAD_REQUEST.value();
    private final Date timestamp = new Date();
    private final String message = "Los argumentos no son válidos";
    private final List<String> errors;
    public ListErrorMessage(List<String> errors){this.errors = errors;}
}
