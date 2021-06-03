package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String federalEntity;
    private String municipality;
    private String location;
    private String street;
    private String cp;
}
