package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.util.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CareerDTO {

    @NotEmpty(groups = {UpdateCareer.class, DeleteCareer.class, CreateStudent.class, UpdateStudent.class})
    private String id;

    @NotEmpty(groups = {CreateCareer.class, UpdateCareer.class})
    private String name;

    @NotEmpty(groups = {CreateCareer.class, UpdateCareer.class})
    private String degree;

    @Valid
    @NotNull(groups = {CreateCareer.class, UpdateCareer.class})
    private DivisionDTO division;

    @NotNull(groups = {UpdateCareer.class})
    private Boolean enabled;

    public Career cloneEntity() {
        return new Career(getId(), getName(), getDegree(),
                getDivision() != null ? getDivision().cloneEntity() : null, getEnabled());
    }
}