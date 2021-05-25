package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Logbook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogbookRepository extends MongoRepository<Logbook,String> {
}
