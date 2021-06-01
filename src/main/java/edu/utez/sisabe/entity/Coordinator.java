package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Document
@Getter
@Setter
@ToString
public class Coordinator {


    public Coordinator(String id, String name, String lastname, User user, Division division) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.user = user;
        this.division = division;
    }

    @Id
    private String id;
    private String name;
    private String lastname;
    private User user;
    private Division division;
}
