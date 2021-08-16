package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Application;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application,String> {

    @Aggregation(value = {"{$lookup: {from: 'announcement', localField: 'announcement._id', foreignField: '_id', as: 'announcement'}}",
            "{$unwind: '$announcement'}",
            "{$match:{$and:[{'division._id':{$lte: ?0}},{validated:false},{'announcement.enabled':true},{'announcement.startDate':{$lte: ?1}},{'announcement.finalDate':{$gte: ?1}}]}}",
            "{$lookup: {from: 'scholarship', localField: 'announcement.scholarship._id', foreignField: '_id', as: 'announcement.scholarship'}}",
            "{$unwind: '$announcement.scholarship'}",
            "{$lookup: {from: 'student', localField: 'student._id', foreignField: '_id', as: 'student'}}",
            "{$unwind: '$student'}",
            "{$lookup: {from: 'career', localField: 'student.career._id', foreignField: '_id', as: 'student.career'}}",
            "{$unwind: '$student.career'}",
            })
    List<Application> findAllByValidatedFalseAndDivision_IdAndActual(String id, LocalDate actual);

    @Aggregation(value = {"{$lookup: {from: 'announcement', localField: 'announcement._id', foreignField: '_id', as: 'announcement'}}",
            "{$unwind: '$announcement'}",
            "{$match:{$and:[{'division._id':{$lte: ?0}},{'validated':true},{'announcement.enabled':true},{'announcement.startDate':{$lte: ?1}},{'announcement.finalDate':{$gte: ?1}}]}}",
            "{$lookup: {from: 'scholarship', localField: 'announcement.scholarship._id', foreignField: '_id', as: 'announcement.scholarship'}}",
            "{$unwind: '$announcement.scholarship'}",
            "{$lookup: {from: 'student', localField: 'student._id', foreignField: '_id', as: 'student'}}",
            "{$unwind: '$student'}",
            "{$lookup: {from: 'career', localField: 'student.career._id', foreignField: '_id', as: 'student.career'}}",
            "{$unwind: '$student.career'}",
            "{$lookup: {from: 'coordinator', localField: 'coordinator._id', foreignField: '_id', as: 'coordinator'}}",
            "{$unwind: '$coordinator'}"
    })
    List<Application> findAllByValidatedTrueAndDivision_IdAndActual(String id, LocalDate actual);

    @Aggregation(value = {"{$match:{$and:[{validated:true},{'student._id':?0}]}}",
            "{$lookup: {from: 'announcement', localField: 'announcement._id', foreignField: '_id', as: 'announcement'}}",
            "{$unwind: '$announcement'}",
            "{$lookup: {from: 'scholarship', localField: 'announcement.scholarship._id', foreignField: '_id', as: 'announcement.scholarship'}}",
            "{$unwind: '$announcement.scholarship'}",
            "{$lookup: {from: 'student', localField: 'student._id', foreignField: '_id', as: 'student'}}",
            "{$unwind: '$student'}",
            "{$lookup: {from: 'career', localField: 'student.career._id', foreignField: '_id', as: 'student.career'}}",
            "{$unwind: '$student.career'}",
            "{$lookup: {from: 'coordinator', localField: 'coordinator._id', foreignField: '_id', as: 'coordinator'}}",
            "{$unwind: '$coordinator'}"})
    List<Application> findAllByStudent_IdAAndValidatedTrue(String id);

    @Aggregation(value = {"{$match:{$and:[{validated:false},{'student._id':?0}]}}",
            "{$lookup: {from: 'announcement', localField: 'announcement._id', foreignField: '_id', as: 'announcement'}}",
            "{$unwind: '$announcement'}",
            "{$lookup: {from: 'scholarship', localField: 'announcement.scholarship._id', foreignField: '_id', as: 'announcement.scholarship'}}",
            "{$unwind: '$announcement.scholarship'}",
            "{$lookup: {from: 'student', localField: 'student._id', foreignField: '_id', as: 'student'}}",
            "{$unwind: '$student'}",
            "{$lookup: {from: 'career', localField: 'student.career._id', foreignField: '_id', as: 'student.career'}}",
            "{$unwind: '$student.career'}"})
    List<Application> findAllByStudent_IdAAndValidatedFalse(String id);

    Application findApplicationById(String id);

    Application save(Application application);

    boolean existsByStudent_Id(String id);

    boolean existsByIdAndValidated(String id, Boolean validated);

    boolean existsByStudent_IdAndAnnouncement_Id(String student_id, String announcement_id);
}
