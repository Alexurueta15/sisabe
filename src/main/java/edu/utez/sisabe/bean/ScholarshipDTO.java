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

    @NotEmpty(groups = {UpdateScholarship.class, DeleteScholarship.class})
    private String id;

    @NotEmpty(groups = {UpdateScholarship.class, CreateScholarship.class})
    private String name;

    @NotEmpty(groups = {UpdateScholarship.class, CreateScholarship.class})
    private String description;

    @NotEmpty(groups = {CreateScholarship.class})
    private String image;

    public Scholarship cloneEntity(){
        return new Scholarship(getId(),getName(),getDescription(),getImage());
    }
}
