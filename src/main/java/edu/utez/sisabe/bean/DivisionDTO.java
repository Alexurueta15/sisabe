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

    @NotEmpty(message = "El identificador de la división no puede ser nulo",
            groups = {CreateCareer.class, UpdateCareer.class,
                    UpdateDivision.class, DeleteDivision.class,
                    UpdateCoordinator.class, CreateCoordinator.class})
    private String id;

    @NotEmpty(message = "El nombre de la división no puede ser nulo",
            groups = {CreateDivision.class, UpdateDivision.class})
    private String name;

    @NotEmpty(message = "El acrónimo de la división no puede ser nulo",
            groups = {CreateDivision.class, UpdateDivision.class})
    private String acronym;

    @NotNull(message = "El estatus de la división no puede ser nulo",
            groups = {UpdateDivision.class})
    private Boolean enabled;

    public Division cloneEntity(){
        return new Division(getId(),getName(), getAcronym(), getEnabled());
    }
}