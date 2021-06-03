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
    private Integer previousQuarter; //
    private Integer currentQuarter; //
    private Float grade; //
    private String reason;
    private Announcement announcement;
    private String gradeReport; //
    private String birthCertificate; //
    private List<String> birthCertificateChild; //
    private String activity;
    private Boolean veredict; //
    private String comment;
    private Integer discount; //
    private Coordinator coordinator;
}
