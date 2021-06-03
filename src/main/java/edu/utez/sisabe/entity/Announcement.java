package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Announcement {

    @Id
    private String id;
    private String period;
    private Date startDate;
    private Date finalDate;
    private Scholarship scholarship;
    private boolean enabled;
}
