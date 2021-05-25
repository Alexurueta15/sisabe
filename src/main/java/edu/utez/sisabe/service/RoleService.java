package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Role;
import edu.utez.sisabe.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role){roleRepository.save(role);}

    public Role findByRole(String role){
        return roleRepository.findByRole(role);
    }
}