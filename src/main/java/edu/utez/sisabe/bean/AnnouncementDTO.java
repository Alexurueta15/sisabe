package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Scholarship;
import edu.utez.sisabe.util.group.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {

    @NotEmpty(groups = {UpdateAnnouncement.class, DeleteAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private String id;

    @NotEmpty(groups = {CreateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private String period;

    @NotNull(groups = {CreateAnnouncement.class, UpdateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private LocalDate startDate;

    @NotNull(groups = {CreateAnnouncement.class, UpdateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private LocalDate finalDate;

    @NotNull(groups = {CreateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private Scholarship scholarship;

    public Announcement cloneEntity() {
        return new Announcement(getId(), getPeriod(), getStartDate(), getFinalDate(), getScholarship());
    }
}
