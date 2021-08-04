package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.*;
import edu.utez.sisabe.repository.ApplicationRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final LogbookService logbookService;

    private final AnnouncementService announcementService;

    private final StudentService studentService;

    private final CoordinatorService coordinatorService;

    private final ScholarshipService scholarshipService;

    public ApplicationService(ApplicationRepository applicationRepository, LogbookService logbookService,
                              AnnouncementService announcementService, StudentService studentService,
                              CoordinatorService coordinatorService, ScholarshipService scholarshipService) {
        this.applicationRepository = applicationRepository;
        this.logbookService = logbookService;
        this.announcementService = announcementService;
        this.studentService = studentService;
        this.coordinatorService = coordinatorService;
        this.scholarshipService = scholarshipService;
    }

    public List<Application> findAllCoordinator(){

        Coordinator coordinator = coordinatorService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Application> applications = new ArrayList<>();
        applications.addAll(applicationRepository.findAllByValidatedFalseAndDivision_IdAndActual(coordinator.getDivision().getId(), LocalDate.now()));
        applications.addAll(applicationRepository.findAllByValidatedTrueAndDivision_IdAndActual(coordinator.getDivision().getId(), LocalDate.now()));

        return applications;
    }

    public List<Application> findAllStudent(){

        Student student = studentService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Application> applications = new ArrayList<>();
        applications.addAll(applicationRepository.findAllByStudent_IdAAndValidatedFalse(student.getId()));
        applications.addAll(applicationRepository.findAllByStudent_IdAAndValidatedTrue(student.getId()));

        return applications;
    }

    public boolean save(Application application){

        Announcement announcement = announcementService.findById(application.getAnnouncement().getId());
        announcement.setScholarship(scholarshipService.findById(announcement.getScholarship().getId()));
        if (application.getGradeReport()==null)
            return false;

        if (announcement.getScholarship().getCategory().equals("Madre soltera")){

            if (application.getBirthCertificate()==null || application.getBirthCertificateChild()==null || application.getBirthCertificateChild().size() == 0)
                return false;
        }
        if (announcement.getScholarship().getCategory().equals("Extracurricular")){

            if (application.getActivity()==null)
                return false;
        }
        Student student = studentService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        application.setStudent(new Student(student.getId()));
        application.setAnnouncement(new Announcement(announcement.getId()));
        application.setRegistrationDate(new Date());
        application.setValidated(false);
        application.setVeredict(false);
        application = applicationRepository.save(application);
        logbookService.save(application);
        return true;
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
                new Division(prevApplication.getDivision().getId()),true,newApplication.getVeredict(),
                newApplication.getComment(), newApplication.getDiscount(), new Coordinator(coordinator.getId()));

        applicationRepository.save(newApplication);
        logbookService.update(prevApplication,newApplication);

        return applicationRepository.existsByIdAndValidated(newApplication.getId(), newApplication.getValidated());
    }


    public boolean existsById(String id){
        return applicationRepository.existsById(id);
    }

    public boolean existsByStudentAndAnnouncement(String idAnnouncement){
        Student student = studentService.findByUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return applicationRepository.existsByStudent_IdAndAnnouncement_Id(student.getId(), idAnnouncement);
    }

}
