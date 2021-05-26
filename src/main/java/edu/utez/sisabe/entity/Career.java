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
public class Career {

    public Career(String id, String name, Division division) {
        this.id = id;
        this.name = name;
        this.division = division;
        this.enabled = true;
    }

    @Id
    private String id;
    private String name;
    private Division division;
    private boolean enabled;
}
