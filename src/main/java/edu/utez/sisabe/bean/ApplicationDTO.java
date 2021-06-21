package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.util.group.CreateApplication;
import edu.utez.sisabe.util.group.DeleteApplication;
import edu.utez.sisabe.util.group.UpdateApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {


    @NotEmpty(groups = {UpdateApplication.class, DeleteApplication.class})
    private String id;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private Integer previousQuarter;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private Integer currentQuarter;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private Float grade;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private String reason;

    @Valid
    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private AnnouncementDTO announcementDTO;

    @NotEmpty(groups = {CreateApplication.class})
    private String gradeReport;

    @NotEmpty
    private String birthCertificate;

    @NotEmpty
    private List<String> birthCertificateChild;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private String activity;

    public Application cloneEntity(){
        return new Application(getId(), getPreviousQuarter(), getCurrentQuarter(), getGrade(), getReason(),
                getAnnouncementDTO().cloneEntity(), getGradeReport(), getBirthCertificate(),
                getBirthCertificateChild(), getActivity());
    }
}
