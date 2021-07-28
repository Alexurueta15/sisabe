package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Career;
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
        student.setUser(new User(prevStudent.getUser().getId()));
        student.setCareer(new Career(prevStudent.getCareer().getId()));
        studentRepository.save(student);
        logbookService.update(prevStudent,student);
    }

    public void delete (String id){
        Student prevStudent = studentRepository.findStudentById(id);
        Student student = new Student(prevStudent.getId(), prevStudent.getName(), prevStudent.getLastname(),
                prevStudent.getCurp(), prevStudent.getBirthDate(), prevStudent.getGender(),
                prevStudent.getNationality(), prevStudent.getAddress(), prevStudent.getPhone(),
                prevStudent.getPhoneHome(), prevStudent.getEnrollment(), prevStudent.getCareer(),
                userService.findUserById(prevStudent.getUser().getId()), prevStudent.getShift());
        student.getUser().setEnabled(!student.getUser().getEnabled());
        userService.update(student.getUser());
        logbookService.update(prevStudent,student);
    }

    public boolean existById(String id){
        return studentRepository.existsById(id);
    }

    public Student findByUsername(String username){
        User user = userService.loadUserByUsername(username);
        return studentRepository.findStudentByUser_Id(user.getId());
    }
}
