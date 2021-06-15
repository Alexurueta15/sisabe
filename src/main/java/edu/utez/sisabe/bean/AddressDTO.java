package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Address;
import edu.utez.sisabe.util.group.CreateStudent;
import edu.utez.sisabe.util.group.UpdateStudent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AddressDTO {

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String federalEntity;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String municipality;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String location;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String street;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String cp;

    public Address cloneEntity (){
        return new Address(getFederalEntity(),getMunicipality(),getLocation(),getStreet(),getCp());
    }
}
