package edu.utez.sisabe.service;

import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Scholarship;
import org.springframework.stereotype.Service;
import edu.utez.sisabe.repository.AnnouncementRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository annoucementRepository;

    private final LogbookService logbookService;

    public AnnouncementService(AnnouncementRepository annoucementRepository, LogbookService logbookService) {
        this.annoucementRepository = annoucementRepository;
        this.logbookService = logbookService;
    }

    public List<Announcement> findAll() {
        return annoucementRepository.findAll();
    }

    public List<Announcement> findAllByEnabledTrueAndValid() {
        return annoucementRepository.findAllByEnabledTrueAndValid(LocalDate.now());
    }

    public boolean save(Announcement announcement) {
        announcement = annoucementRepository.save(announcement);
        logbookService.save(announcement);
        return existsById(announcement.getId());
    }

    public void update(Announcement announcement) {
        Announcement prevAnnouncement = annoucementRepository.findAnnouncementById(announcement.getId());
        announcement.setPeriod(prevAnnouncement.getPeriod());
        announcement.setEnabled(prevAnnouncement.getEnabled());
        announcement.setScholarship(prevAnnouncement.getScholarship());
        annoucementRepository.save(announcement);
        logbookService.update(prevAnnouncement, announcement);
    }

    public void delete(String id) {
        Announcement prevAnnouncement = annoucementRepository.findAnnouncementById(id);
        Announcement newAnnouncement = new Announcement(prevAnnouncement.getId(), prevAnnouncement.getPeriod(),
                prevAnnouncement.getStartDate(), prevAnnouncement.getFinalDate(),
                new Scholarship(prevAnnouncement.getScholarship().getId()), !prevAnnouncement.getEnabled());

        annoucementRepository.save(newAnnouncement);
        logbookService.update(prevAnnouncement, newAnnouncement);
    }

    public boolean existsById(String id) {
        return annoucementRepository.existsById(id);
    }
}
