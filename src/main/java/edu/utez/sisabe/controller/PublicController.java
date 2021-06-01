package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.service.CareerService;
import edu.utez.sisabe.service.DivisionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

    private final DivisionService divisionService;

    private final CareerService careerService;

    public PublicController(DivisionService divisionService, CareerService careerService) {
        this.divisionService = divisionService;
        this.careerService = careerService;
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
        if (divisionService.findDivisionById(idDivision)==null)
            return new ErrorMessage("División ingresada no existente");
        return careerService.findAllByEnabledTrueAndDivision_id(idDivision);
    }
}
