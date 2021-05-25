package edu.utez.sisabe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("logbook")
@Getter
@Setter
@NoArgsConstructor
public class Logbook {

    @Id
    private String id;
    private String sender;
    private String uri;
    private String method;
    private String senderIP;
    private Date date;
    private Object previousData;
    private Object newData;

    public Logbook(String username, String uri, String senderIP, String method) {
        this.uri = uri;
        this.sender = username;
        this.senderIP = senderIP;
        this.method = method;
        this.date = new Date();
        this.previousData = null;
        this.newData = null;
    }
}
