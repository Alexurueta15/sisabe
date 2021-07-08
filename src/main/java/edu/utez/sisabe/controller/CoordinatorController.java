package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ApplicationDTO;
import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.SuccessMessage;
import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.service.ApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/coordinador")
public class CoordinatorController {

    private final ApplicationService applicationService;

    public CoordinatorController (ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping("/application")
    public List<Application> findAll(){
        return applicationService.findAll();
    }

    @PostMapping("/application")
    public Object saveVeredict(ApplicationDTO applicationDTO){
        if (applicationService.saveVeredict(applicationDTO.cloneEntitytoVeredict())){
            return new SuccessMessage("Solicitud valorada correctamente");
        }else {
            return new ErrorMessage("Solicitud no valorada");
        }

    }
}
