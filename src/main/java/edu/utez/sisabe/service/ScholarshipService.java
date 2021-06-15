package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Scholarship;
import edu.utez.sisabe.repository.ScholarshipRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Service
public class ScholarshipService {

    private final LogbookService logbookService;

    private final ScholarshipRepository scholarshipRepository;

    public ScholarshipService(LogbookService logbookService, ScholarshipRepository scholarshipRepository) {
        this.logbookService = logbookService;
        this.scholarshipRepository = scholarshipRepository;
    }

    public List<Scholarship> findAll() {
        return scholarshipRepository.findAll();
    }

    public List<Scholarship> findAllByEnabledTrue() {
        return scholarshipRepository.findAllByEnabledTrue();
    }

    public boolean save(Scholarship scholarship) {
        scholarship = scholarshipRepository.save(scholarship);
        logbookService.save(scholarship);
        return existById(scholarship.getId());
    }

    public void update(Scholarship scholarship) {
        Scholarship prevScholarship = scholarshipRepository.findScholarshipById(scholarship.getId());
        scholarship.setEnabled(prevScholarship.getEnabled());
        if (scholarship.getImage() == null)
            scholarship.setImage(prevScholarship.getImage());
        scholarshipRepository.save(scholarship);
        logbookService.update(prevScholarship, scholarship);
    }

    public void delete(String id) {
        Scholarship prevScholarship = scholarshipRepository.findScholarshipById(id);
        Scholarship newScholarship = new Scholarship(prevScholarship.getId(), prevScholarship.getName(),
                prevScholarship.getDescription(), prevScholarship.getImage(), !prevScholarship.getEnabled());
        scholarshipRepository.save(newScholarship);
        logbookService.update(prevScholarship, newScholarship);
    }

    public boolean existById(String id) {
        return scholarshipRepository.existsById(id);
    }
}
