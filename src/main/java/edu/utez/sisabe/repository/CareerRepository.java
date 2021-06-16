package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Career;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    boolean existsByName(String name);

    boolean existsByDegree(String degree);

    Career findCareerById(String id);

    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAll();

    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAllByEnabledTrue();

    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAllByEnabledTrueAndDivision_Id(String idDivision);

    @Aggregation(value = {"{$match: {enabled: true, degree: '?0', 'division._id': '?1'}}",
            "{$lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'}}"})
    List<Career> findAllByEnabledTrueAndDegreeAndDivision_Id(String degree, String idDivision);
}
