package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Scholarship;
import edu.utez.sisabe.util.group.CreateScholarship;
import edu.utez.sisabe.util.group.DeleteScholarship;
import edu.utez.sisabe.util.group.UpdateScholarship;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ScholarshipDTO {

    @NotEmpty(message = "El identificador no puede ser nulo",
            groups = {UpdateScholarship.class, DeleteScholarship.class})
    private String id;

    @NotEmpty(message = "El nombre de la beca no puede ser nulo",
            groups = {UpdateScholarship.class, CreateScholarship.class})
    private String name;

    @NotEmpty(message = "La descripción de la beca no puede ser nula",
            groups = {UpdateScholarship.class, CreateScholarship.class})
    private String description;

    @NotEmpty(message = "La imagen no puede ser nula",
            groups = {CreateScholarship.class})
    private String image;

    private Boolean enabled;

    public Scholarship cloneEntity(){
        return new Scholarship(getId(),getName(),getDescription(),getImage(),getEnabled());
    }
}
