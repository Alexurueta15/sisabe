package edu.utez.sisabe.controller;

import edu.utez.sisabe.entity.Logbook;
import edu.utez.sisabe.service.LogbookService;
import edu.utez.sisabe.service.RoleService;
import edu.utez.sisabe.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rootAdrress}/admin")
public class AdministratorController {

    private final UserService userService;

    private final RoleService roleService;

    private final LogbookService logbookService;

    private final String successMessage;

    public AdministratorController(UserService userService, RoleService roleService,
                                   LogbookService logbookService) {
        this.userService = userService;
        this.roleService = roleService;
        this.logbookService = logbookService;
        this.successMessage = "Operación exitosa";
    }

    @GetMapping("/logbook")
    public List<Logbook> findAllLogbook(){
        return logbookService.findAll();
    }


}