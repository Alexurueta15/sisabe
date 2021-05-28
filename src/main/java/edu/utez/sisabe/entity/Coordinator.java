package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Coordinator {

    public Coordinator(String id, String name, String lastname, User user, Division division, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.user = user;
        this.division = division;
        this.enabled = enabled;
    }

    @Id
    private String id;
    private String name;
    private String lastname;
    private User user;
    private Division division;
    private Boolean enabled;
}
