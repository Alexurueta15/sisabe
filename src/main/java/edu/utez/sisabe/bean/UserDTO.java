package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.util.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(groups = {UpdateUser.class, DeleteUser.class, UpdateCoordinator.class})
    private String id;

    @Email(groups = {UpdateUser.class, CreateUser.class, CreateCoordinator.class})
    @NotEmpty(groups = {UpdateUser.class, CreateUser.class, CreateCoordinator.class})
    private String username;

    private String password;

    public User cloneEntity() {
        return new User(getId(), getUsername(), getPassword());
    }
}