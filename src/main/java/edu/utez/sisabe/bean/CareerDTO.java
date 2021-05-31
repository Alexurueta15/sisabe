package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.util.group.CreateCareer;
import edu.utez.sisabe.util.group.DeleteCareer;
import edu.utez.sisabe.util.group.UpdateCareer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CareerDTO {

    @NotEmpty(message = "El identificador de la carrera no puede ser nulo",
            groups = {UpdateCareer.class, DeleteCareer.class})
    private String id;

    @NotEmpty(message = "El nombre de la carrera no puede ser nulo",
            groups = {CreateCareer.class, UpdateCareer.class})
    private String name;

    @NotEmpty(message = "El nombre del nivel académico no puede ser nulo",
            groups = {CreateCareer.class, UpdateCareer.class})
    private String degree;

    @Valid
    @NotNull(message = "Se necesitan datos de la División Académica",
            groups = {CreateCareer.class, UpdateCareer.class})
    private DivisionDTO division;

    @NotNull(message = "El estatus de la carrera no puede ser nulo",
            groups = {UpdateCareer.class})
    private Boolean enabled;

    public Career cloneEntity() {
        return new Career(getId(), getName(), getDegree(), getDivision().cloneEntity(), getEnabled());
    }
}
