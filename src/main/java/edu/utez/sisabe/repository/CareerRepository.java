package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    boolean existsByName(String name);
    Career findCareerById(String id);
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Career> findAll();
    List<Career> findAllByEnabledTrue();
    List<Career> findAllByEnabledTrueAndDivision_Id(String idDivision);
}
