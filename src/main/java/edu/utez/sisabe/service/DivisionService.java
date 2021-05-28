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

    private final LogbookService logbookService;

    public DivisionService(DivisionRepository divisionRepository, LogbookService logbookService) {
        this.divisionRepository = divisionRepository;
        this.logbookService = logbookService;
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
    }

    public void delete(String id) {
        Division prevDivision = divisionRepository.findDivisionById(id);
        Division division = new Division(prevDivision.getId(), prevDivision.getName(),
                prevDivision.getAcronym(), prevDivision.getEnabled());
        division.setEnabled(false);
        divisionRepository.save(division);
        logbookService.update(prevDivision, division);
    }
}
