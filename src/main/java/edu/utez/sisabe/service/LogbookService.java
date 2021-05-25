package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Logbook;
import edu.utez.sisabe.repository.LogbookRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogbookService {


    private final LogbookRepository logbookRepository;

    public LogbookService(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    public List<Logbook> findAll(){
        return logbookRepository.findAll();
    }

    public void save(Object newData) {
        Logbook requestRecord = (Logbook) SecurityContextHolder.getContext().getAuthentication().getDetails();
        requestRecord.setNewData(newData);
        logbookRepository.save(requestRecord);
    }

    public void update(Object previousData, Object newData) {
        Logbook requestRecord = (Logbook) SecurityContextHolder.getContext().getAuthentication().getDetails();
        requestRecord.setPreviousData(previousData);
        requestRecord.setNewData(newData);
        logbookRepository.save(requestRecord);
    }

    public void delete(Object previousData) {
        Logbook requestRecord = (Logbook) SecurityContextHolder.getContext().getAuthentication().getDetails();
        requestRecord.setPreviousData(previousData);
        logbookRepository.save(requestRecord);
    }
}