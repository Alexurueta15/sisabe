package edu.utez.sisabe.bean;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class SuccessMessage {
    private final int statusCode = HttpStatus.OK.value();
    private final Date timestamp = new Date();
    private final String message = "Operación exitosa";
    private final String description;
    public SuccessMessage(String description) {
        this.description = description;
    }
}
