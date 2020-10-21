package com.epstein.entity;

import com.epstein.model.DateInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity @Table(name = "messages_schema")
public class MessageSchema {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int message_schema_id;

    @Column(name = "title") private String title;
    @Column(name = "text") private String text;
    @Column(name = "link") private String link;


    public MessageSchema() {    }

    public int getId() {
        return message_schema_id;
    }
    public void setId(int id) {
        this.message_schema_id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link == null ?
                "null" : link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public boolean hasLink() {
        return link != null;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
