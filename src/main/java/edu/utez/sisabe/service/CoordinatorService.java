package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.repository.CoordinatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatorService {

    private final CoordinatorRepository coordinatorRepository;

    private final LogbookService logbookService;

    public CoordinatorService(CoordinatorRepository coordinatorRepository, LogbookService logbookService) {
        this.coordinatorRepository = coordinatorRepository;
        this.logbookService = logbookService;
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

    public void save(Coordinator coordinator) {
        coordinatorRepository.save(coordinator);
        logbookService.save(coordinator);
    }

    public void update(Coordinator coordinator) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(coordinator.getId());
        coordinatorRepository.save(coordinator);
        logbookService.update(prevCoordinator, coordinator);
    }

    public void delete(String id) {
        Coordinator prevCoordinator = coordinatorRepository.findCoordinatorById(id);
        Coordinator coordinator = new Coordinator(prevCoordinator.getId(),
                prevCoordinator.getName(),
                prevCoordinator.getEmail(),
                prevCoordinator.getDivision());
        coordinator.setEnabled(false);
        logbookService.update(prevCoordinator, coordinator);
        coordinatorRepository.save(coordinator);
    }
}
