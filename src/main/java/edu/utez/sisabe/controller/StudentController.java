package edu.utez.sisabe.controller;

import edu.utez.sisabe.bean.ErrorMessage;
import edu.utez.sisabe.bean.StudentDTO;
import edu.utez.sisabe.bean.SuccessMessage;
import edu.utez.sisabe.service.StudentService;
import edu.utez.sisabe.util.group.UpdateStudent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/estudiante")
public class StudentController {

    private StudentService studentService;

    private StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PutMapping("/student")
    private Object updateStudent (@Validated(UpdateStudent.class) @RequestBody StudentDTO studentDTO){
        if (!studentService.existById(studentDTO.getId()))
            return new ErrorMessage("No existe estudiante registrado");
        studentService.update(studentDTO.cloneEntity());
        return new SuccessMessage("Estudiante actualizado");
    }

}
