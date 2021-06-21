package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Scholarship;
import edu.utez.sisabe.util.group.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {

    @NotEmpty(groups = {UpdateAnnouncement.class, DeleteAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private String id;

    @NotEmpty(groups = {UpdateAnnouncement.class, CreateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private String period;

    @NotEmpty(groups = {UpdateAnnouncement.class, CreateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private Date startDate;

    @NotEmpty(groups = {UpdateAnnouncement.class, CreateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private Date finalDate;

    @NotNull(groups = {CreateAnnouncement.class, UpdateAnnouncement.class, CreateApplication.class, UpdateApplication.class})
    private Scholarship scholarship;

    private Boolean enabled;

    public Announcement cloneEntity() {
        return new Announcement(getId(), getPeriod(), getStartDate(), getFinalDate(), getScholarship(), getEnabled());
    }
}
