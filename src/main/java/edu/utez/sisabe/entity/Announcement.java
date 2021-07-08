package edu.utez.sisabe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Announcement {

    public Announcement(String id, String period, LocalDate startDate, LocalDate finalDate, Scholarship scholarship) {
        this.id = id;
        this.period = period;
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.scholarship = scholarship;
        this.enabled = null;
    }

    public Announcement(String id) {
        this.id = id;
    }

    @Id
    private String id;
    private String period;
    private LocalDate startDate;
    private LocalDate finalDate;
    private Scholarship scholarship;
    private Boolean enabled;
}
