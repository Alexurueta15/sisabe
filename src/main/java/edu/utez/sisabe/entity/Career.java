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
  
    public Career(String id, String name, String degree, Division division, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.degree = degree;
        this.division = division;
        this.enabled = enabled;
        this.degree = degree;
    }

    @Id
    private String id;
    private String name;
    private String degree;
    private Division division;
    private Boolean enabled;
    private String degree;
}
