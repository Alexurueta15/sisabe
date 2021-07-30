package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.entity.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatorRepository extends MongoRepository<Coordinator, String> {
    Coordinator findCoordinatorById(String id);
    @Aggregation(value = {"{$lookup: {from: 'division', localField: 'division._id', foreignField: '_id', as: 'division'}}",
            "{$lookup: {from: 'user', localField: 'user._id', foreignField: '_id', as: 'user'}}"})
    List<Coordinator> findAll();

    Coordinator findCoordinatorByUser_Id(String id);

}
