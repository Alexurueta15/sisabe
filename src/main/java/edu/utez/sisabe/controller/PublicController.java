package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.SuccessMessage;
import edu.utez.sisabe.bean.UserDTO;
import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.service.CareerService;
import edu.utez.sisabe.service.DivisionService;
import edu.utez.sisabe.service.UserService;
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

    public PublicController(DivisionService divisionService, CareerService careerService, UserService userService) {
        this.divisionService = divisionService;
        this.careerService = careerService;
        this.userService = userService;
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

    @GetMapping("/career/{division}")
    public Object findAllCareerByDivision(
            @PathVariable(name = "division") @NotEmpty(message = "El identificador de la división no puede ser nulo")
                    String idDivision) {
        if (divisionService.findDivisionById(idDivision) == null)
            return new ErrorMessage("División ingresada no existente");
        return careerService.findAllByEnabledTrueAndDivision_id(idDivision);
    }
}
