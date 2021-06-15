package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement,String> {

    Announcement findAnnouncementById(String id);
    List<Announcement> findAllByEnabledTrue();
}
