package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.entity.User;
import edu.utez.sisabe.repository.CoordinatorRepository;
import edu.utez.sisabe.util.EmailService;
import edu.utez.sisabe.util.PasswordGenerator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;

    private final LogbookService logbookService;

    private final UserService userService;

    private final EmailService emailService;

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

    public void save(Coordinator coordinator) throws MessagingException {
        String passGenerated = PasswordGenerator.getPassword();
        coordinator.getUser().setPassword(passGenerated);
        coordinator.getUser().setRole("Comité");
        User newUser = userService.save(coordinator.getUser());
        coordinator.setUser(newUser);
        coordinatorRepository.save(coordinator);
        logbookService.save(coordinator);
        emailService.sendEmail(coordinator.getUser().getUsername(), passGenerated);
    }

    public void update(Coordinator coordinator) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(coordinator.getId());
        coordinator.setUser(prevCoordinator.getUser());
        coordinator.setDivision(new Division(coordinator.getDivision().getId()));
        coordinatorRepository.save(coordinator);
        logbookService.update(prevCoordinator, coordinator);
    }

    public void delete(String id) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(id);
        Coordinator coordinator = new Coordinator(prevCoordinator.getId(), prevCoordinator.getName(),
                prevCoordinator.getLastname(), prevCoordinator.getUser(),
                new Division(prevCoordinator.getDivision().getId()));
        coordinator.getUser().setEnabled(!prevCoordinator.getUser().getEnabled());
        userService.update(coordinator.getUser());
        coordinator.setUser(new User(prevCoordinator.getUser().getId()));
        logbookService.update(prevCoordinator, coordinator);
        coordinatorRepository.save(coordinator);
    }
}
