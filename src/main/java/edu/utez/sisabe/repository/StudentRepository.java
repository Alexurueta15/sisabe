package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.entity.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    @Aggregation(value = {"{$match: {'user._id': ?0}}",
            "{$lookup: {from: 'career', localField: 'career._id', foreignField: '_id', as: 'career'}}",
            "{$unwind: '$career'}",
            "{$lookup: {from:'division', localField: 'career.division._id', foreignField: '_id', as: 'career.division'}}",
            "{$unwind: '$career.division'}",
            "{$lookup: {from: 'user', localField: 'user._id', foreignField: '_id', as: 'user'}}",
            "{$unwind: '$user'}"})
    Student findStudentByUser(String user);

    Student findStudentById(String id);

    @Aggregation(value = {"{$lookup: {from: 'career', localField: 'career._id', foreignField: '_id', as: 'career'}}",
            "{$unwind: '$career'}",
            "{$lookup: {from:'division', localField: 'career.division._id', foreignField: '_id', as: 'career.division'}}",
            "{$unwind: '$career.division'}",
            "{$lookup: {from: 'user', localField: 'user._id', foreignField: '_id', as: 'user'}}",
            "{$unwind: '$user'}"})
    List<Student> findAll();

    List<Student> findAllByCareer_Division(Division division);

    boolean existsById(String id);

    Student findStudentByUser_Id(String id);
}
