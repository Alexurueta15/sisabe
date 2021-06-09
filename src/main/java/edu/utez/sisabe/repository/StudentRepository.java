package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    Student findStudentById(String id);
    List<Student> findAllByCareer_Division(Division division);
    boolean existsById(String id);
}
