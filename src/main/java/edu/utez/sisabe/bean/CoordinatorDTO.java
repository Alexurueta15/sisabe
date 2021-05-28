package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.util.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CoordinatorDTO {

    @NotEmpty(message = "El identificador del cordinador no puede ser nulo",
            groups = {UpdateDivision.class, DeleteDivision.class})
    private String id;

    @NotEmpty(message = "El nombre del coordinador no puede ser nulo",
            groups = {CreateCoordinator.class, UpdateCoordinator.class})
    private String name;

    @NotEmpty(message = "El apellido del coordinador no puede ser nulo",
            groups = {CreateCoordinator.class, UpdateCoordinator.class})
    private String lastname;

    @Valid
    @NotNull(message = "Se necesitan datos de usuario",
            groups = {CreateCoordinator.class})
    private UserDTO user;

    @Valid
    @NotNull(message = "Se necesitan datos de la División Académica",
            groups = {UpdateCoordinator.class, CreateCoordinator.class})
    private DivisionDTO division;

    private Boolean enabled;

    public Coordinator cloneEntity(){
        return new Coordinator(getId(), getName(), getLastname(), getUser().cloneEntity(),
                getDivision().cloneEntity(), getEnabled());
    }
}
