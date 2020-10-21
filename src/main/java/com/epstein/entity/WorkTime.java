package com.epstein.entity;

import com.epstein.model.DateInfo;
import com.epstein.model.TimeInfo;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Table(name = "work_times")
public class WorkTime {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int workTimeId;

    @Column(name = "date") private Date date;
    @Column(name = "time_in") private Time timeIn;
    @Column(name = "time_out") private Time timeOut;
    @OneToOne @JoinColumn(name = "user_id")
    private User user;

    public WorkTime() {    }

    public int getId() {
        return workTimeId;
    }
    public void setId(int workTimeId) {
        this.workTimeId = workTimeId;
    }

    public DateInfo getDate() {
        return new DateInfo( this.date.toLocalDate() );
    }
    public void setDate(LocalDate date) {
        this.date = Date.valueOf(date);
    }

    public TimeInfo getTimeIn() {
        return new TimeInfo( timeIn.toLocalTime() );
    }
    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = Time.valueOf(timeIn);
    }

    public TimeInfo getTimeOut() {
        return new TimeInfo( timeOut.toLocalTime() );
    }
    public void setTimeOut(LocalTime timeOut) {

        this.timeOut = Time.valueOf(timeOut);
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return this.getTimeOut() != null ?
                this.getDate() + " " + this.getTimeIn() + " -> " + this.getTimeOut() :
                this.getDate() + " " + this.getTimeIn() + " -> --- ";
    }
}
