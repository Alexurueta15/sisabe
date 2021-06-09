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

    @NotEmpty(groups = {UpdateDivision.class, DeleteDivision.class})
    private String id;

    @NotEmpty(groups = {CreateCoordinator.class, UpdateCoordinator.class})
    private String name;

    @NotEmpty(groups = {CreateCoordinator.class, UpdateCoordinator.class})
    private String lastname;

    @Valid @NotNull(groups = {CreateCoordinator.class})
    private UserDTO user;

    @Valid @NotNull(groups = {UpdateCoordinator.class, CreateCoordinator.class})
    private DivisionDTO division;

    public Coordinator cloneEntity() {
        return new Coordinator(getId(), getName(), getLastname(), getUser() != null ? getUser().cloneEntity() : null,
                getDivision().cloneEntity());
    }
}