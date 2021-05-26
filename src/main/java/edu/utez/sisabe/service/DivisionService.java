package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Coordinator;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.repository.DivisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;

    private final CareerService careerService;

    private final LogbookService logbookService;

    private final CoordinatorService coordinatorService;

    public DivisionService(DivisionRepository divisionRepository, CareerService careerService, LogbookService logbookService, CoordinatorService coordinatorService) {
        this.divisionRepository = divisionRepository;
        this.careerService = careerService;
        this.logbookService = logbookService;
        this.coordinatorService = coordinatorService;
    }

    public List<Division> findAll() {
        return divisionRepository.findAll();
    }

    public List<Division> findAllByEnabledTrue() {
        return divisionRepository.findAllByEnabledTrue();
    }

    public void save(Division division) {
        divisionRepository.save(division);
        logbookService.save(division);
    }

    public void update(Division division) {
        Division prevDivision = divisionRepository.findDivisionById(division.getId());
        divisionRepository.save(division);
        logbookService.update(prevDivision, division);
        List<Career> careers = careerService.findAllByDivision(division);
        for (Career career :
                careers) {
            career.setDivision(division);
        }
        careerService.saveAll(careers);
        List<Coordinator> coordinators = coordinatorService.findAllByDivision(division);
        for (Coordinator coordinator :
                coordinators) {
            coordinator.setDivision(division);
        }
        coordinatorService.saveAll(coordinators);
    }

    public void delete(String id) {
        Division prevDivision = divisionRepository.findDivisionById(id);
        Division division = new Division(prevDivision.getId(), prevDivision.getName(), prevDivision.getAcronym());
        division.setEnabled(false);
        divisionRepository.save(division);
        logbookService.update(prevDivision, division);
    }
}
