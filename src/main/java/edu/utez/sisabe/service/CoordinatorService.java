package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.entity.Role;
import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.CoordinatorRepository;
import edu.utez.sisabe.util.EmailService;
import edu.utez.sisabe.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;

    private final LogbookService logbookService;

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CoordinatorService(CoordinatorRepository coordinatorRepository, LogbookService logbookService,
                              UserService userService, EmailService emailService) {
        this.coordinatorRepository = coordinatorRepository;
        this.logbookService = logbookService;
        this.userService = userService;
        this.emailService = emailService;
    }

    public List<Coordinator> findAll() {
        return coordinatorRepository.findAll();
    }

    public void saveAll(List<Coordinator> coordinators){
        coordinatorRepository.saveAll(coordinators);
    }

    public List<Coordinator> findAllByDivision(Division division) {
        return coordinatorRepository.findAllByDivision(division);
    }

    public List<Coordinator> findAllByEnabledTrue() {
        return coordinatorRepository.findAllByEnabledTrue();
    }

    public void save(Coordinator coordinator) throws MessagingException {
        String passGenerated = PasswordGenerator.getPassword();
            coordinator.getUser().setPassword(passGenerated);
            coordinator.getUser().setRole(new Role("Comité"));
            User newUser = userService.save(coordinator.getUser());
            coordinator.setUser(newUser);
            coordinatorRepository.save(coordinator);
            logbookService.save(coordinator);
            emailService.sendEmail(coordinator.getUser().getUsername(), passGenerated);
    }

    public void update(Coordinator coordinator) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(coordinator.getId());
        coordinator.setUser(prevCoordinator.getUser());
        coordinatorRepository.save(coordinator);
        logbookService.update(prevCoordinator, coordinator);
    }

    public void delete(String id) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(id);
        Coordinator coordinator = new Coordinator(prevCoordinator.getId(), prevCoordinator.getName(),
                prevCoordinator.getLastname(), prevCoordinator.getUser(),
                prevCoordinator.getDivision(), prevCoordinator.getEnabled());
        coordinator.setEnabled(false);
        coordinator.getUser().setEnabled(false);
        userService.save(coordinator.getUser());
        logbookService.update(prevCoordinator, coordinator);
        coordinatorRepository.save(coordinator);
    }
}
