package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.*;
import edu.utez.sisabe.repository.ApplicationRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final LogbookService logbookService;

    private final AnnouncementService announcementService;

    private final StudentService studentService;

    private final CoordinatorService coordinatorService;

    public ApplicationService(ApplicationRepository applicationRepository, LogbookService logbookService,
                              AnnouncementService announcementService, StudentService studentService,
                              CoordinatorService coordinatorService) {
        this.applicationRepository = applicationRepository;
        this.logbookService = logbookService;
        this.announcementService = announcementService;
        this.studentService = studentService;
        this.coordinatorService = coordinatorService;
    }

    public List<Application> findAll(){
        Coordinator coordinator = coordinatorService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return applicationRepository.findAllByDivision_IdAndActual(coordinator.getDivision().getId(), LocalDate.now());
    }

    public boolean save(Application application){

        Announcement announcement = announcementService.findById(application.getAnnouncement().getId());
        if (application.getGradeReport()==null)
            return false;

        if (application.getAnnouncement().getScholarship().getCategory().equals("Madres solteras")){

            if (application.getBirthCertificate()==null || application.getBirthCertificateChild()==null || application.getBirthCertificateChild().size() == 0)
                return false;
        }
        if (application.getAnnouncement().getScholarship().getCategory().equals("Deportiva")){

            if (application.getActivity()==null)
                return false;
        }
        application.setAnnouncement(new Announcement(announcement.getId()));
        application.setRegistrationDate(new Date());
        application.setValidated(false);
        application.setVerdict(false);
        application = applicationRepository.save(application);
        logbookService.save(application);
        return existsById(application.getId());
    }

    public void update (Application newApplication){
        Application prevApplication = applicationRepository.findApplicationById(newApplication.getId());
        Announcement announcement = newApplication.getAnnouncement();

        newApplication.setGradeReport(
                newApplication.getGradeReport()==null ?
                        prevApplication.getGradeReport() :
                        newApplication.getGradeReport()
        );

        if (announcement.getScholarship().getCategory().equals("Madres solteras")){
            newApplication.setBirthCertificate(
                    newApplication.getBirthCertificate()==null ?
                            prevApplication.getBirthCertificate() :
                            newApplication.getBirthCertificate());
            newApplication.setBirthCertificateChild(
                    newApplication.getBirthCertificateChild()==null ?
                            prevApplication.getBirthCertificateChild() :
                            newApplication.getBirthCertificateChild()
            );
        }
        if (announcement.getScholarship().getCategory().equals("Deportiva")){
            newApplication.setActivity(
                    newApplication.getActivity()==null ?
                            prevApplication.getActivity() :
                            newApplication.getActivity()
            );
        }
    }

    public boolean saveVeredict (Application newApplication){

        if (newApplication.getComment() == null || newApplication.getDiscount() == null)
            return false;

        Coordinator coordinator = coordinatorService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Application prevApplication = applicationRepository.findApplicationById(newApplication.getId());

        newApplication = new Application(newApplication.getId(), prevApplication.getRegistrationDate(),
                prevApplication.getPreviousQuarter(),
                prevApplication.getCurrentQuarter(), prevApplication.getGrade(), prevApplication.getReason(),
                new Announcement(prevApplication.getAnnouncement().getId()), prevApplication.getGradeReport(),
                prevApplication.getBirthCertificate(), prevApplication.getBirthCertificateChild(),
                prevApplication.getActivity(),new Student(prevApplication.getStudent().getId()),
                new Division(prevApplication.getDivision().getId()),true,newApplication.getVerdict(),
                newApplication.getComment(), newApplication.getDiscount(), new Coordinator(coordinator.getId()));

        applicationRepository.save(newApplication);
        logbookService.update(prevApplication,newApplication);

        return applicationRepository.existsByIdAndValidated(newApplication.getId(), newApplication.getValidated());
    }


    public boolean existsById(String id){
        return applicationRepository.existsById(id);
    }

    public boolean existsByStudent_Id(String id){
        return applicationRepository.existsByStudent_Id(id);
    }

}
