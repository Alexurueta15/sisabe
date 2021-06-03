package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Scholarship {

    public Scholarship(String id) {
        this.id = id;
    }

    @Id
    private String id;
    private String name;
    private String description;
    private String image; //Checar
    private Boolean enabled;
}
