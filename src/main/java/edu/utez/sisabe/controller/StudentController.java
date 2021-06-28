package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.*;
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

    private StudentController(StudentService studentService, ApplicationService applicationService) {
        this.studentService = studentService;
        this.applicationService = applicationService;
    }

    @GetMapping
    private Student getStudent(){
        return studentService.getStudentInSession();
    }

    @PutMapping
    private Object updateStudent(@Validated(UpdateStudent.class) @RequestBody StudentDTO studentDTO) {
        if (!studentService.existById(studentDTO.getId()))
            return new ErrorMessage("No existe estudiante registrado");
        studentService.update(studentDTO.cloneEntity());
        return new SuccessMessage("Estudiante actualizado");
    }

    @PostMapping("/application")
    private Object saveApplication(@Validated(CreateApplication.class) @RequestBody ApplicationDTO applicationDTO) {
        if (!applicationDTO.getAnnouncementDTO().getScholarship().getCategory().equals("Académica")) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<String>> violations = validator.validate(applicationDTO.getBirthCertificate());
            Set<ConstraintViolation<List<String>>> violations2 = validator.validate(applicationDTO.getBirthCertificateChild());
            if (violations.isEmpty() && violations2.isEmpty()) {
                applicationService.save(applicationDTO.cloneEntity());
                return new SuccessMessage("Solicitud de beca Registrada");
            } else {
                List<String> errors = new ArrayList<>();
                for (ConstraintViolation<String> violation : violations) {
                    errors.add(violation.getMessage());
                    errors.add(violation.getMessageTemplate());
                }
                for (ConstraintViolation<List<String>> violation : violations2) {
                    errors.add(violation.getMessage());
                    errors.add(violation.getMessageTemplate());
                }
                return new ListErrorMessage(errors);
            }
        } else {
            return new SuccessMessage("Solicitud de beca Registrada");
        }
    }
}
