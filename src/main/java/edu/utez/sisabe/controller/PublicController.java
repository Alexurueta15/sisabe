package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.StudentDTO;
import edu.utez.sisabe.bean.SuccessMessage;
import edu.utez.sisabe.bean.UserDTO;
import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.service.*;
import edu.utez.sisabe.util.group.CreateStudent;
import edu.utez.sisabe.util.group.CreateUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

    private final DivisionService divisionService;

    private final CareerService careerService;

    private final UserService userService;

    private final StudentService studentService;

    private final AnnouncementService announcementService;

    public PublicController(DivisionService divisionService, CareerService careerService,
                            UserService userService, StudentService studentService, AnnouncementService announcementService) {
        this.divisionService = divisionService;
        this.careerService = careerService;
        this.userService = userService;
        this.studentService = studentService;
        this.announcementService = announcementService;
    }

    @PutMapping("/user")
    public Object updatePassword(@Validated(CreateUser.class) @RequestBody UserDTO userDTO) throws MessagingException {
        if (!userService.existsByUsername(userDTO.getUsername()))
            return new ErrorMessage("Correo electrónico no existente");
        userService.updatePassword(userDTO.cloneEntity());
        return new SuccessMessage("Contraseña de usuario actualizada");
    }

    @GetMapping("/division")
    public List<Division> findAllDivision() {
        return divisionService.findAllByEnabledTrue();
    }

    @GetMapping("/career")
    public List<Career> findAllCareer() {
        return careerService.findAllByEnabledTrue();
    }

    @GetMapping("/career/{degree}/division/{division}")
    public Object findAllCareerByDegreeAndDivision(
            @PathVariable(name = "degree") @NotEmpty(message = "El nivel académico no puede ser nulo")
                    String degree,
            @PathVariable(name = "division") @NotEmpty(message = "El identificador de la división no puede ser nulo")
                    String idDivision) {
        if (!careerService.existsByDegree(degree))
            return new ErrorMessage("Nivel académico ingresado no existente");
        if (divisionService.findDivisionById(idDivision) == null)
            return new ErrorMessage("División ingresada no existente");
        return careerService.findAllByEnabledTrueAndDegreeAndDivision_id(degree, idDivision);
    }

    @GetMapping("/career/division/{division}")
    public Object findAllCareerByDivision(
            @PathVariable(name = "division") @NotEmpty(message = "El identificador de la división no puede ser nulo")
                    String idDivision) {
        if (divisionService.findDivisionById(idDivision) == null)
            return new ErrorMessage("División ingresada no existente");
        return careerService.findAllByEnabledTrueAndDivision_id(idDivision);
    }

    @GetMapping("/announcement")
    public List<Announcement> findAllAnnouncement() {
        return announcementService.findAllByEnabledTrueAndValid();
    }

    @PostMapping("/student")
    public Object saveStudent(@Validated(CreateStudent.class) @RequestBody StudentDTO studentDTO){
        if (userService.existsByUsername(studentDTO.getUser().getUsername()))
            return new ErrorMessage("Usuario existente");
        if (careerService.findCareerById(studentDTO.getCareer().getId()) == null)
            return new ErrorMessage("Carrera ingresada no existente");
        if (studentService.save(studentDTO.cloneEntity()))
            return new SuccessMessage("Estudiente registrado");
        else
            return new ErrorMessage("Estudiante no registrado");
    }
}
