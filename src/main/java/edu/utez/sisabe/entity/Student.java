package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Student {

    @Id
    private String id;
    private String name;
    private String lastname;
    private String curp;
    private String birthDate;
    private Character gender;
    private String nationality;
    private Address address;
    private String phone;
    private String phoneHome;
    private String enrollment;
    private Career career;
    private User user;
    private String shift;
    private List<Application> applications;

}
