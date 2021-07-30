package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Student;
import edu.utez.sisabe.util.group.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @NotEmpty(groups = {UpdateStudent.class, DeleteStudent.class, CreateApplication.class, UpdateApplication.class})
    private String id;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String name;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String lastname;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String curp;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String birthDate;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    @Size(max = 1, min = 1, groups = {UpdateStudent.class, CreateStudent.class})
    private String gender;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String nationality;

    @Valid
    @NotNull(groups = {UpdateStudent.class, CreateStudent.class})
    private AddressDTO address;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String phone;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String phoneHome;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String enrollment;

    @Valid
    @NotNull(groups = CreateStudent.class)
    private CareerDTO career;

    @Valid
    @NotNull(groups = CreateStudent.class)
    private UserDTO user;

    @NotEmpty(groups = {UpdateStudent.class, CreateStudent.class})
    private String shift;

    public Student cloneEntity() {
        return new Student(getId(), getName(), getLastname(), getCurp(), getBirthDate(), getGender(),
                getNationality(), getAddress().cloneEntity(), getPhone(), getPhoneHome(), getEnrollment(),
                getCareer() != null ? getCareer().cloneEntity() : null,
                getUser() != null ? getUser().cloneEntity() : null, getShift());
    }
}
