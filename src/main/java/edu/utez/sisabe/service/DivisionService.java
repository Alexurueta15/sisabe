package edu.utez.sisabe.service;

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

    public boolean existsByAcronym(String acronym) {
        return divisionRepository.existsByAcronym(acronym);
    }

    public boolean existsByName(String name) {
        return divisionRepository.existsByName(name);
    }

    public List<Division> findAll() {
        return divisionRepository.findAll();
    }

    public Division findDivisionById(String id) {
        return divisionRepository.findDivisionById(id);
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
        if (!prevDivision.getAcronym().equals(division.getAcronym()) && existsByAcronym(division.getAcronym()))
            division.setAcronym(prevDivision.getAcronym());
        if (!prevDivision.getName().equals(division.getName()) && existsByName(division.getName()))
            division.setName(prevDivision.getName());
        divisionRepository.save(division);
        logbookService.update(prevDivision, division);
    }

    public void delete(String id) {
        Division prevDivision = divisionRepository.findDivisionById(id);
        Division division = new Division(prevDivision.getId(), prevDivision.getName(),
                prevDivision.getAcronym(), prevDivision.getEnabled());
        division.setEnabled(!prevDivision.getEnabled());
        divisionRepository.save(division);
        logbookService.update(prevDivision, division);
    }
}
