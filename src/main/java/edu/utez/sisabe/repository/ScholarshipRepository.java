package edu.utez.sisabe.repository;


import edu.utez.sisabe.entity.Scholarship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends MongoRepository<Scholarship,String> {

    List<Scholarship> findAllByEnabledTrue();
    Scholarship findScholarshipById(String id);
}
