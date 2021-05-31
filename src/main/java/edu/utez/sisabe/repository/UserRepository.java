package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserById(String id);

    User findUserByUsername(String username);

    List<User> findAllByRole(String role);

    boolean existsByUsername(String username);
}