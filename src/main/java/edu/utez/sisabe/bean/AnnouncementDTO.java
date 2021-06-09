package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Announcement;
import edu.utez.sisabe.entity.Scholarship;
import edu.utez.sisabe.util.group.CreateAnnouncement;
import edu.utez.sisabe.util.group.DeleteAnnouncement;
import edu.utez.sisabe.util.group.UpdateAnnouncement;
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

    @NotEmpty(message = "El identificador no puede ser nulo",
            groups = {UpdateAnnouncement.class, DeleteAnnouncement.class})
    private String id;

    @NotEmpty(message = "El periodo no puede ser nulo",
            groups = {UpdateAnnouncement.class, CreateAnnouncement.class})
    private String period;

    @NotEmpty(message = "La fecha de inicio no puede ser nula",
            groups = {UpdateAnnouncement.class, CreateAnnouncement.class})
    private Date startDate;

    @NotEmpty(message = "La fecha final no puede ser nula",
            groups = {UpdateAnnouncement.class, CreateAnnouncement.class})
    private Date finalDate;

    @NotNull(message = "Se necesitan datos de la beca",
            groups = {CreateAnnouncement.class, UpdateAnnouncement.class})
    private Scholarship scholarship;

    private Boolean enabled;

    public Announcement cloneEntity (){
        return new Announcement(getId(),getPeriod(),getStartDate(),getFinalDate(),getScholarship(),getEnabled());
    }
}
