package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.util.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CoordinatorDTO {

    @NotEmpty(message = "El identificador del cordinador no puede ser nulo",
            groups = {UpdateDivision.class, DeleteDivision.class})
    private String id;

    @NotEmpty(message = "El nombre del coordinador no puede ser nulo",
            groups = {CreateCoordinator.class, UpdateCoordinator.class})
    private String name;

    @Email(message = "El nombre de usuario debe ser un correo electrónico" ,
            groups = {UpdateCoordinator.class, CreateCoordinator.class})
    @NotEmpty(message = "El nombre de usuario no puede ser nulo",
            groups = {UpdateCoordinator.class, CreateCoordinator.class})
    private String email;

    @Valid
    @NotEmpty(message = "El nombre de la carrera no puede ser nulo",
            groups = {UpdateCoordinator.class, CreateCoordinator.class})
    private DivisionDTO divisionDTO;

    public Coordinator cloneEntity(){
        return new Coordinator(getId(), getName(), getEmail(), getDivisionDTO().cloneEntity());
    }
}
