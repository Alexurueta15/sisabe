package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.*;
import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.service.AnnouncementService;
import edu.utez.sisabe.entity.Student;
import edu.utez.sisabe.service.ApplicationService;
import edu.utez.sisabe.service.StudentService;
import edu.utez.sisabe.util.group.CreateApplication;
import edu.utez.sisabe.util.group.UpdateStudent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/estudiante")
public class StudentController {

    private final StudentService studentService;

    private final ApplicationService applicationService;

    private final AnnouncementService announcementService;

    private StudentController(StudentService studentService, ApplicationService applicationService, AnnouncementService announcementService) {
        this.studentService = studentService;
        this.applicationService = applicationService;
        this.announcementService = announcementService;
    }

    @GetMapping
    private Student getStudent(){
        return studentService.getStudentInSession();
    }

    @PutMapping
    public Object updateStudent(@Validated(UpdateStudent.class) @RequestBody StudentDTO studentDTO) {
        if (!studentService.existById(studentDTO.getId()))
            return new ErrorMessage("No existe estudiante registrado");
        studentService.update(studentDTO.cloneEntity());
        return new SuccessMessage("Estudiante actualizado");
    }

    @GetMapping("/application")
    public List<Application> findAll(){
        return applicationService.findAllStudent();
    }

    @PostMapping("/application")
    public Object saveApplication(@Validated(CreateApplication.class) @RequestBody ApplicationDTO applicationDTO) {
        if (announcementService.existsById(applicationDTO.getAnnouncement().getId())) {

             if (!applicationService.existsByStudentAndAnnouncement(applicationDTO.getAnnouncement().getId())){
                if(applicationService.save(applicationDTO.cloneEntity()))
                    return new SuccessMessage("Solicitud de beca Registrada");
                else
                    return new ErrorMessage("Solicitud de beca no registrada, revisa tus datos");
            } else {
                 return new ErrorMessage("Solicitud de beca ya registrada");
            }
        } else {
            return new ErrorMessage("Convocatoria no existente");
        }
    }
}
