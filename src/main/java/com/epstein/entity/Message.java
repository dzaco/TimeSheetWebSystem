package com.epstein.entity;

import com.epstein.model.DateInfo;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity @Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int message_id;

    @OneToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToOne @JoinColumn(name = "message_schema_id")
    private MessageSchema messageSchema;

    @Column(name = "date") private Date date;
    @Column(name = "end_date") private Date endDate;

    @Column(name = "status") private int status;

    public int getId() {
        return message_id;
    }

    public void setId(int id) {
        this.message_id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageSchema getMessageSchema() {
        return messageSchema;
    }

    public void setMessageSchema(MessageSchema messageSchema) {
        this.messageSchema = messageSchema;
    }

    public DateInfo getDate() {
        return new DateInfo( date.toLocalDate() );
    }
    public void setDate(LocalDate date) {
        this.date = Date.valueOf(date);
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = Date.valueOf(endDate);
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


    public String getText() {
        return this.getMessageSchema().getText();
    }
    public void setText(String text) {
        this.getMessageSchema().setText(text);
    }

    public String getTitle() {
        return this.getMessageSchema().getTitle();
    }
    public void setTitle(String title) {
        this.getMessageSchema().setTitle(title);
    }

    public String getLink() {
        return this.getMessageSchema().getLink();
    }
    public void setLink(String link) {
        this.getMessageSchema().setLink(link);
    }
    public boolean hasLink() {
        return this.getMessageSchema().hasLink();
    }

    @Override
    public String toString() {
        return this.getText();
    }

}
