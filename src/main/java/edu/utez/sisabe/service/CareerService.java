package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Career;
import edu.utez.sisabe.entity.Division;
import edu.utez.sisabe.repository.CareerRepository;
import edu.utez.sisabe.repository.DivisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    private final CareerRepository careerRepository;

    private final LogbookService logbookService;

    public CareerService(CareerRepository careerRepository, LogbookService logbookService) {
        this.careerRepository = careerRepository;
        this.logbookService = logbookService;
    }

    public List<Career> findAll() {
        return careerRepository.findAll();
    }

    public List<Career> findAllByDivision(Division division) {
        return careerRepository.findAllByDivision(division);
    }

    public List<Career> findAllByEnabledTrue() {
        return careerRepository.findAllByEnabledTrue();
    }

    public void save(Career career) {
        careerRepository.save(career);
        logbookService.save(career);
    }

    public void saveAll(List<Career> careers) {
        careerRepository.saveAll(careers);
    }

    public void update(Career career) {
        Career prevCareer = careerRepository.findCareerById(career.getId());
        careerRepository.save(career);
        logbookService.update(prevCareer, career);
    }

    public void delete(String id) {
        Career prevCareer = careerRepository.findCareerById(id);
        Career career = new Career(prevCareer.getId(), prevCareer.getName(),
                prevCareer.getDivision(), prevCareer.getEnabled());
        career.setEnabled(false);
        careerRepository.save(career);
        logbookService.update(prevCareer, career);
    }
}
