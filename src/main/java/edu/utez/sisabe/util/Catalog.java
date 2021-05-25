package edu.utez.sisabe.util;

import edu.utez.sisabe.entity.Role;
import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.UserRepository;
import edu.utez.sisabe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Catalog implements CommandLineRunner {


    private final RoleService roleService;

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Catalog(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args)  {
        String[] roles = {"Administrador", "Alumno", "Comité"};
        User defaultUser = new User();

        for (String role : roles) {
            if (roleService.findByRole(role) == null) {
                roleService.save(new Role(role));
            }
        }

        defaultUser.setUsername("sisabe@localhost.com");
        if (!userRepository.existsByUsername(defaultUser.getUsername())){
            defaultUser.setPassword(bCryptPasswordEncoder.encode("Uno234"));
            defaultUser.setRole(roleService.findByRole("Administrador"));
            userRepository.save(defaultUser);
        }
    }
}
