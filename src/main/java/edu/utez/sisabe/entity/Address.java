package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Address {

    private String federalEntity;
    private String municipality;
    private String location;
    private String street;
    private String cp;
}
