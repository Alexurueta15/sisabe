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

    public Coordinator(String id, String name, String email, Division division) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.division = division;
        this.enabled = true;
    }

    @Id
    private String id;
    private String name;
    private String email;
    private Division division;
    private boolean enabled;
}
