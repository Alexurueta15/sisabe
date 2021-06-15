package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.*;
import edu.utez.sisabe.entity.*;
import edu.utez.sisabe.service.*;
import edu.utez.sisabe.util.group.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdministratorController {

    private final DivisionService divisionService;

    private final CareerService careerService;

    private final CoordinatorService coordinatorService;

    private final LogbookService logbookService;

    private final UserService userService;

    private final ScholarshipService scholarshipService;

    private final StudentService studentService;

    public AdministratorController(DivisionService divisionService, CareerService careerService,
                                   CoordinatorService coordinatorService, LogbookService logbookService,
                                   UserService userService, ScholarshipService scholarshipService,
                                   StudentService studentService) {
        this.divisionService = divisionService;
        this.careerService = careerService;
        this.coordinatorService = coordinatorService;
        this.logbookService = logbookService;
        this.userService = userService;
        this.scholarshipService = scholarshipService;
        this.studentService = studentService;
    }

    @GetMapping("/user")
    public List<User> findAllUser(){
        return userService.findAll();
    }

    @GetMapping("/logbook")
    public List<Logbook> findAllLogbook() {
        return logbookService.findAll();
    }

    @GetMapping("/division")
    public List<Division> findAllDivision() {
        return divisionService.findAll();
    }

    @PostMapping("/division")
    public Object saveDivision(@Validated(CreateDivision.class) @RequestBody DivisionDTO divisionDTO) {
        if (divisionService.existsByAcronym(divisionDTO.getAcronym()))
            return new ErrorMessage("El acrónimo de división ya existe");
        if (divisionService.existsByName(divisionDTO.getName()))
            return new ErrorMessage("El nombre de división ya existe");
        divisionDTO.setEnabled(true);
        divisionService.save(divisionDTO.cloneEntity());
        return new SuccessMessage("División registrada");
    }

    @PutMapping("/division")
    public Object UpdateDivision(@Validated(UpdateDivision.class) @RequestBody DivisionDTO divisionDTO) {
        if (divisionService.findDivisionById(divisionDTO.getId()) == null)
            return new ErrorMessage("División ingresada no existente");
        divisionService.update(divisionDTO.cloneEntity());
        return new SuccessMessage("División actualizada");
    }

    @DeleteMapping("/division")
    public Object deleteDivision(@Validated(DeleteDivision.class) @RequestBody DivisionDTO divisionDTO) {
        if (divisionService.findDivisionById(divisionDTO.getId()) == null)
            return new ErrorMessage("División ingresada no existente");
        divisionService.delete(divisionDTO.getId());
        return new SuccessMessage("Se ha cambiado el estatus de la división");
    }

    @GetMapping("/career")
    public List<Career> findAllCareers() {
        return careerService.findAll();
    }

    @PostMapping("/career")
    public Object saveCareer(@Validated(CreateCareer.class) @RequestBody CareerDTO careerDTO) {
        if (careerService.existsByName(careerDTO.getName()))
            return new ErrorMessage("El nombre de carrera ya existe");
        careerDTO.setEnabled(true);
        careerService.save(careerDTO.cloneEntity());
        return new SuccessMessage("Carrera registrada");
    }

    @PutMapping("/career")
    public Object UpdateCareer(@Validated(UpdateCareer.class) @RequestBody CareerDTO careerDTO) {
        if (careerService.findCareerById(careerDTO.getId()) == null)
            return new ErrorMessage("Carrera ingresada no existente");
        careerService.update(careerDTO.cloneEntity());
        return new SuccessMessage("Carrera actualizada");
    }

    @DeleteMapping("/career")
    public Object deleteCareer(@Validated(DeleteCareer.class) @RequestBody CareerDTO careerDTO) {
        if (careerService.findCareerById(careerDTO.getId()) == null)
            return new ErrorMessage("Carrera ingresada no existente");
        careerService.delete(careerDTO.getId());
        return new SuccessMessage("Se ha cambiado el estatus de la carrera");
    }

    @GetMapping("/coordinator")
    public List<Coordinator> findAllCoordinators() {
        return coordinatorService.findAll();
    }

    @PostMapping("/coordinator")
    public Object saveCoordinator(@Validated(CreateCoordinator.class) @RequestBody CoordinatorDTO coordinatorDTO)
            throws MessagingException {
        if (userService.existsByUsername(coordinatorDTO.getUser().getUsername()))
            return new ErrorMessage("Usuario existente");
        if (divisionService.findDivisionById(coordinatorDTO.getDivision().getId()) == null)
            return new ErrorMessage("División ingresada no existente");
        Coordinator coordinator = coordinatorDTO.cloneEntity();
        coordinator.getUser().setRole("Comité");
        coordinatorService.save(coordinator);
        return new SuccessMessage("Coordinador registrado");
    }

    @PutMapping("/coordinator")
    public Object UpdateCoordinator(@Validated(UpdateCoordinator.class) @RequestBody CoordinatorDTO coordinatorDTO) {
        coordinatorService.update(coordinatorDTO.cloneEntity());
        return new SuccessMessage("Coordinador actualizado");
    }

    @DeleteMapping("/coordinator")
    public Object deleteCoordinator(@Validated(DeleteCoordinator.class) @RequestBody CoordinatorDTO coordinatorDTO) {
        coordinatorService.delete(coordinatorDTO.getId());
        return new SuccessMessage("Se ha cambiado el estatus del coordinador");
    }

    @GetMapping("/scholarship")
    public List<Scholarship> findAllSholarship() {
        return scholarshipService.findAll();
    }

    @PostMapping("/scholarship")
    public Object saveScholarship(@Validated(CreateScholarship.class) @RequestBody ScholarshipDTO scholarshipDTO) {
        Scholarship scholarship = scholarshipDTO.cloneEntity();
        scholarship.setEnabled(true);
        if (scholarshipService.save(scholarship))
            return new SuccessMessage("Beca registrada");
        else
            return new ErrorMessage("Beca no registrada");
    }

    @PutMapping("/scholarship")
    public Object updateScholarship(@Validated(UpdateScholarship.class) @RequestBody ScholarshipDTO scholarshipDTO) {
        if (!scholarshipService.existById(scholarshipDTO.getId()))
            return new ErrorMessage("No existe beca registrada");
        scholarshipService.update(scholarshipDTO.cloneEntity());
        return new SuccessMessage("Beca actualizada");
    }

    @DeleteMapping("/scholarship")
    public Object deleteScholarship(@Validated(DeleteScholarship.class) @RequestBody ScholarshipDTO scholarshipDTO) {
        if (!scholarshipService.existById(scholarshipDTO.getId()))
            return new ErrorMessage("No existe beca registrada");
        scholarshipService.delete(scholarshipDTO.getId());
        return new SuccessMessage("Se ha cambiado el estatus de la beca");
    }

    @GetMapping("/student")
    public List<Student> findAllStudent(){
        return studentService.findAll();
    }

    @DeleteMapping("/student")
    public Object deleteStudent(@Validated(DeleteStudent.class) @RequestBody StudentDTO studentDTO){
        if (!studentService.existById(studentDTO.getId()))
            return new ErrorMessage("No existe estudiante registrado");
        studentService.delete(studentDTO.getId());
        return new SuccessMessage("Se ha cambiado el estado del estudiante");
    }

}