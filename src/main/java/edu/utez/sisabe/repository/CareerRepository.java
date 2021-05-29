package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    Career findCareerById(String id);
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAll();
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAllByEnabledTrue();
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAllByDivision(Division division);
}
