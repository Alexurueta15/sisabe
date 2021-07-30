package edu.utez.sisabe.bean;

import edu.utez.sisabe.entity.Application;
import edu.utez.sisabe.util.group.CreateApplication;
import edu.utez.sisabe.util.group.DeleteApplication;
import edu.utez.sisabe.util.group.UpdateApplication;
import edu.utez.sisabe.util.group.UpdateApplicationVeredict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {


    @NotEmpty(groups = {UpdateApplication.class, DeleteApplication.class, UpdateApplicationVeredict.class})
    private String id;

    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private Integer previousQuarter;

    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private Integer currentQuarter;

    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private Float grade;

    @NotEmpty(groups = {CreateApplication.class, UpdateApplication.class})
    private String reason;

    @Valid
    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private AnnouncementDTO announcement;

    @NotEmpty(groups = {CreateApplication.class})
    private String gradeReport;

    private String birthCertificate;

    private List<String> birthCertificateChild;

    private String activity;

    @Valid
    @NotNull(groups = {CreateApplication.class, UpdateApplication.class})
    private DivisionDTO division;

    @NotNull(groups = {UpdateApplicationVeredict.class})
    private Boolean veredict;

    @NotEmpty(groups = {UpdateApplicationVeredict.class})
    private String comment;

    @NotNull(groups = {UpdateApplicationVeredict.class})
    private Integer discount;

    public Application cloneEntity(){
        return new Application(getId(), getPreviousQuarter(), getCurrentQuarter(), getGrade(), getReason(),
                getAnnouncement().cloneEntity(), getGradeReport(), getBirthCertificate(),
                getBirthCertificateChild(), getActivity(), division.cloneEntity());
    }

    public Application cloneEntitytoVeredict(){
        return new Application(getId(), getVeredict(), getComment(), getDiscount());
    }
}
