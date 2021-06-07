package edu.utez.sisabe.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class ErrorMessage {
    private final int statusCode = HttpStatus.BAD_REQUEST.value();
    private final Date timestamp = new Date();
    private final String message = "Operación fallida";
    private final String description;
    public ErrorMessage(String description) {
        this.description = description;
    }
}
