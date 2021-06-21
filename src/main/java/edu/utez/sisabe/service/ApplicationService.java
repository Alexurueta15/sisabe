package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final LogbookService logbookService;

    public ApplicationService(ApplicationRepository applicationRepository, LogbookService logbookService) {
        this.applicationRepository = applicationRepository;
        this.logbookService = logbookService;
    }

    public void save(Application application){
        applicationRepository.save(application);
        logbookService.save(application);
    }

    public List<Application> findAll(){
        return applicationRepository.findAll();
    }
}
