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
public class Division {

    public Division(String id, String name, String acronym) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.enabled = true;
    }

    @Id
    private String id;
    private String name;
    private String acronym;
    private boolean enabled;
}
