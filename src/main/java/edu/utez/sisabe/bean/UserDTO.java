package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.util.group.CreateUser;
import edu.utez.sisabe.util.group.DeleteUser;
import edu.utez.sisabe.util.group.UpdateUser;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "El identificador de usuario no puede ser nulo",
    groups = {UpdateUser.class, DeleteUser.class})
    private String id;

    @Email(message = "El nombre de usuario debe ser un correo electrónico" ,
            groups = {UpdateUser.class, CreateUser.class})
    @NotEmpty(message = "El nombre de usuario no puede ser nulo",
            groups = {UpdateUser.class, CreateUser.class})
    private String username;

    @NotEmpty(message = "La contraseña de usuario no puede ser nula" ,
            groups = {UpdateUser.class, CreateUser.class})
    @Size(min = 4, max = 16, message = "La contraseña de usuario debe ser de al menos 4 caracteres, máximo 16" ,
            groups = {UpdateUser.class, CreateUser.class})
    @Pattern(regexp = "^(?=.*[A-Z][a-z])(?=.*\\d)[A-Za-z\\d]*$",
            message = "La contraseña debe contener al menos una letra minúscula, una letra mayúscula y " +
                    "un número" ,
            groups = {UpdateUser.class, CreateUser.class})
    private String password;



    public User cloneEntity() {
        return new User(getId(), getUsername(), getPassword());
    }
}