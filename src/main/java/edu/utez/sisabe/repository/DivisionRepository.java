package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Division;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivisionRepository extends MongoRepository<Division, String> {
    Division findDivisionById(String id);
    boolean existsByAcronym(String acronym);
    boolean existsByName(String name);
    List<Division> findAllByEnabledTrue();
}
