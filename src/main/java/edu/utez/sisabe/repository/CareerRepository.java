package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends MongoRepository<Career, String> {
    Career findCareerById(String id);
    List<Career> findAllByEnabledTrue();
    List<Career> findAllByDivision(Division division);
}
