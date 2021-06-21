package edu.utez.sisabe.repository;

import edu.utez.sisabe.entity.Announcement;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    @Aggregation(value = {"{$lookup: {from: 'scholarship', localField: 'scholarship._id', foreignField: '_id', as: 'scholarship'}}",
            "{$unwind: '$scholarship'}"})
    List<Announcement> findAll();

    @Aggregation(value = {"{$match:{$and:[{enabled:true},{startDate:{$lte: ?0}},{finalDate:{$gte: ?0}}]}}",
            "{$lookup: {from: 'scholarship', localField: 'scholarship._id', foreignField: '_id', as: 'scholarship'}}",
            "{$unwind: '$scholarship'}"})
    List<Announcement> findAllByEnabledTrueAndValid(LocalDate date);

    Announcement findAnnouncementById(String id);
}
