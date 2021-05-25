package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "El identificador de usuario no puede ser nulo")
    private String id;

    @Email(message = "El nombre de usuario debe ser un correo electrónico")
    @NotEmpty(message = "El nombre de usuario no puede ser nulo")
    private String username;

    @NotEmpty(message = "La contraseña de usuario no puede ser nula")
    @Size(min = 4, max = 16, message = "La contraseña de usuario debe ser de al menos 4 caracteres, máximo 16")
    @Pattern(regexp = "^(?=.*[A-Z][a-z])(?=.*\\d)[A-Za-z\\d]*$",
            message = "La contraseña debe contener al menos una letra minúscula, una letra mayúscula y " +
                    "un número")
    private String password;

    private RoleDTO role;


    public User cloneEntity() {
        return new User(getId(), getUsername(), getPassword(), getRole() != null ? getRole().cloneEntity() : null);
    }
}