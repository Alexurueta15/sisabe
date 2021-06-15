package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application,String> {
    Application findApplicationById(String id);
}
