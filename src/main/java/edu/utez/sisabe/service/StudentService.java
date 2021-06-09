package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Student;
import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final LogbookService logbookService;

    private final UserService userService;

    public StudentService (StudentRepository studentRepository, LogbookService logbookService, UserService userService){
        this.studentRepository = studentRepository;
        this.logbookService = logbookService;
        this.userService = userService;
    }

    public List<Student> findAll (){
        return studentRepository.findAll();
    }

    public boolean save (Student student){
        student.getUser().setRole("Estudiante");
        User newUser = new User(userService.save(student.getUser()).getId());
        student.setUser(newUser);
        student.setId(studentRepository.save(student).getId());
        logbookService.save(student);

        return existById(student.getId());
    }

    public void update (Student student){
        Student prevStudent = studentRepository.findStudentById(student.getId());
        Student newStudent = new Student(prevStudent.getId(), prevStudent.getName(), prevStudent.getLastname(),
                prevStudent.getCurp(), prevStudent.getBirthDate(), prevStudent.getGender(),
                prevStudent.getNationality(), prevStudent.getAddress(),prevStudent.getPhone(),
                prevStudent.getPhoneHome(), prevStudent.getEnrollment(), prevStudent.getCareer(),
                prevStudent.getUser(), prevStudent.getShift());
    }

    public boolean existById(String id){
        return studentRepository.existsById(id);
    }
}
