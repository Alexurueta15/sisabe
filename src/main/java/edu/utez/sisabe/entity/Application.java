package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Application {

    @Id
    private String id;
    private Integer previousQuarter;
    private Integer currentQuarter;
    private Float grade;
    private String reason;
    private Announcement announcement;
    private String gradeReport;
    private String birthCertificate;
    private List<String> birthCertificateChild;
    private String activity;
    private Boolean verdict;
    private String comment;
    private Integer discount;
    private Coordinator coordinator;
    private Student student;
    private Division division;

    public Application(String id, Integer previousQuarter, Integer currentQuarter, Float grade, String reason,
                       Announcement announcement, String gradeReport, String birthCertificate,
                       List<String> birthCertificateChild, String activity) {
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
    }
}
