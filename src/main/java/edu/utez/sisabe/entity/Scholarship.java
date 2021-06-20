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

    public Scholarship(String id, String name, String category, String description, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.image = image;
        this.enabled = true;
    }

    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private String image;
    private Boolean enabled;
}
