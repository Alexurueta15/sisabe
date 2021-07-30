package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Application {

    @Id
    private String id;
    private Date registrationDate;
    private Integer previousQuarter;
    private Integer currentQuarter;
    private Float grade;
    private String reason;
    private Announcement announcement;
    private String gradeReport;
    private String birthCertificate;
    private List<String> birthCertificateChild;
    private String activity;
    private Student student;
    private Division division;
    private Boolean validated;
    private Boolean veredict;
    private String comment;
    private Integer discount;
    private Coordinator coordinator;


    public Application(String id, Integer previousQuarter, Integer currentQuarter, Float grade, String reason,
                       Announcement announcement, String gradeReport, String birthCertificate,
                       List<String> birthCertificateChild, String activity, Division division) {
        this.id = id;
        this.previousQuarter = previousQuarter;
        this.currentQuarter = currentQuarter;
        this.grade = grade;
        this.reason = reason;
        this.announcement = announcement;
        this.gradeReport = gradeReport;
        this.birthCertificate = birthCertificate;
        this.birthCertificateChild = birthCertificateChild;
        this.activity = activity;
        this.division = division;
    }

    public Application (String id, Boolean veredict, String comment, Integer discount){
        this.id = id;
        this.veredict = veredict;
        this.comment = comment;
        this.discount = discount;
    }

}
