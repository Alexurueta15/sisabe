package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.util.group.CreateCareer;
import edu.utez.sisabe.util.group.DeleteCareer;
import edu.utez.sisabe.util.group.UpdateCareer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CareerDTO {

    @NotEmpty(message = "El identificador de la carrera no puede ser nulo",
            groups = {UpdateCareer.class, DeleteCareer.class})
    private String id;

    @NotEmpty(message = "El nombre de la carrera no puede ser nulo",
            groups = {CreateCareer.class, UpdateCareer.class})
    private String name;

    @Valid
    @NotEmpty(message = "El nombre de la carrera no puede ser nulo",
            groups = {CreateCareer.class, UpdateCareer.class})
    private DivisionDTO divisionDTO;

    public Career cloneEntity(){
        return new Career(getId(), getName(), getDivisionDTO().cloneEntity());
    }
}
