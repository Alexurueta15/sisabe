package edu.utez.sisabe.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class ListErrorMessage {
    private final int statusCode;
    private final Date timestamp;
    private final String message;
    private final List<String> errors;

}
