package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ApplicationDTO;
import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.SuccessMessage;
import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.service.ApplicationService;
import edu.utez.sisabe.util.group.UpdateApplicationVeredict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comite")
public class CoordinatorController {

    private final ApplicationService applicationService;

    public CoordinatorController (ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping("/application")
    public List<Application> findAll(){
        return applicationService.findAllCoordinator();
    }

    @PostMapping("/application")
    public Object saveVeredict(@Validated(UpdateApplicationVeredict.class) @RequestBody ApplicationDTO applicationDTO){
        System.out.println("controller: "+applicationDTO.getComment());
        System.out.println("controller: "+applicationDTO.getDiscount());
        if (applicationService.saveVeredict(applicationDTO.cloneEntitytoVeredict())){
            return new SuccessMessage("Solicitud valorada correctamente");
        }else {
            return new ErrorMessage("Solicitud no valorada, revisa los datos");
        }

    }
}
