package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.util.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DivisionDTO {

    @NotEmpty(groups = {CreateCareer.class, UpdateCareer.class, UpdateDivision.class, DeleteDivision.class,
                    UpdateCoordinator.class, CreateCoordinator.class})
    private String id;

    @NotEmpty(groups = {CreateDivision.class, UpdateDivision.class})
    private String name;

    @NotEmpty(groups = {CreateDivision.class, UpdateDivision.class})
    private String acronym;

    @NotNull(groups = {UpdateDivision.class})
    private Boolean enabled;

    public Division cloneEntity(){
        return new Division(getId(),getName(), getAcronym(), getEnabled());
    }
}