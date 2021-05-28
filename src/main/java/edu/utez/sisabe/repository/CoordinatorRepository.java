package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatorRepository extends MongoRepository<Coordinator, String> {
    Coordinator findCoordinatorById(String id);
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Coordinator> findAllByEnabledTrue();
    @Aggregation("{ $lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'} }")
    List<Coordinator> findAllByDivision(Division division);
}
