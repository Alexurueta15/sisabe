package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByRole(String role);
    Optional<Role> findById(String id);
}
