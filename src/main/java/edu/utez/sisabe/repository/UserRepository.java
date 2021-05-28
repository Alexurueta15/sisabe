package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Role;
import edu.utez.sisabe.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findUserById(String id);

    User findUserByUsername(String username);

    List<User> findAllByRole(Role role);

    boolean existsByUsername(String username);

    User save (User user);
}