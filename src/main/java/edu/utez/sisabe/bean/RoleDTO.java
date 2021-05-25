package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    private String id;
    private String role;

    public Role cloneEntity() {
        return new Role(getId(), getRole());
    }
}
