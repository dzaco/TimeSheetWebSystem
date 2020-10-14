package com.epstein.entity;

import com.epstein.model.DateInfo;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Table(name = "timesheets")
public class Timesheet {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "timesheet_id") private int timesheet_id;

    @OneToOne @JoinColumn(name = "project_id") private Project project;

    @Column(name = "stage") private int stage;

    @OneToOne @JoinColumn(name = "user_id") private User user;

    @Column(name = "month") private int month;
    @Column(name = "year") private int year;

    @Column(name = "day1") private Integer day1;
    @Column(name = "day2") private Integer day2;
    @Column(name = "day3") private Integer day3;
    @Column(name = "day4") private Integer day4;
    @Column(name = "day5") private Integer day5;
    @Column(name = "day6") private Integer day6;
    @Column(name = "day7") private Integer day7;
    @Column(name = "day8") private Integer day8;
    @Column(name = "day9") private Integer day9;
    @Column(name = "day10") private Integer day10;
    @Column(name = "day11") private Integer day11;
    @Column(name = "day12") private Integer day12;
    @Column(name = "day13") private Integer day13;
    @Column(name = "day14") private Integer day14;
    @Column(name = "day15") private Integer day15;
    @Column(name = "day16") private Integer day16;
    @Column(name = "day17") private Integer day17;
    @Column(name = "day18") private Integer day18;
    @Column(name = "day19") private Integer day19;
    @Column(name = "day20") private Integer day20;
    @Column(name = "day21") private Integer day21;
    @Column(name = "day22") private Integer day22;
    @Column(name = "day23") private Integer day23;
    @Column(name = "day24") private Integer day24;
    @Column(name = "day25") private Integer day25;
    @Column(name = "day26") private Integer day26;
    @Column(name = "day27") private Integer day27;
    @Column(name = "day28") private Integer day28;
    @Column(name = "day29") private Integer day29;
    @Column(name = "day30") private Integer day30;
    @Column(name = "day31") private Integer day31;


    public Timesheet() {    }

    public Timesheet(int timesheet_id, @NonNull Project project, int stage, User user, int month, int year, Integer day1, Integer day2, Integer day3, Integer day4, Integer day5, Integer day6, Integer day7, Integer day8, Integer day9, Integer day10, Integer day11, Integer day12, Integer day13, Integer day14, Integer day15, Integer day16, Integer day17, Integer day18, Integer day19, Integer day20, Integer day21, Integer day22, Integer day23, Integer day24, Integer day25, Integer day26, Integer day27, Integer day28, Integer day29, Integer day30, Integer day31) {
        this.timesheet_id = timesheet_id;
        this.project = project;
        this.stage = stage;
        this.user = user;
        this.month = month;
        this.year = year;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day8 = day8;
        this.day9 = day9;
        this.day10 = day10;
        this.day11 = day11;
        this.day12 = day12;
        this.day13 = day13;
        this.day14 = day14;
        this.day15 = day15;
        this.day16 = day16;
        this.day17 = day17;
        this.day18 = day18;
        this.day19 = day19;
        this.day20 = day20;
        this.day21 = day21;
        this.day22 = day22;
        this.day23 = day23;
        this.day24 = day24;
        this.day25 = day25;
        this.day26 = day26;
        this.day27 = day27;
        this.day28 = day28;
        this.day29 = day29;
        this.day30 = day30;
        this.day31 = day31;
    }

    public int getId() {
        return timesheet_id;
    }

    public void setId(int id) {
        this.timesheet_id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMonth() {
        return new DateInfo().getMonthNameOf(month);
    }
    public int getMonthValue() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {return this.year;}
    public void setMonthAndYear(String monthAndYear) {
        String[] split = monthAndYear.split(" ");
        this.setMonth( new DateInfo().getMonthValueOf(split[0]) );
        this.setYear( Integer.parseInt(split[1]));
    }
    public String getMonthAndYear() {
        return this.getMonth() + " " + this.getYear();
    }

    public Integer[] getHours() {

        return new Integer[]{
                this.day1, this.day2, this.day3, this.day4, this.day5, this.day6, this.day7, this.day8, this.day9,
                this.day10, this.day11, this.day12, this.day13, this.day14, this.day15, this.day16, this.day17,
                this.day18, this.day19, this.day20, this.day21, this.day22, this.day23, this.day24, this.day25,
                this.day26, this.day27, this.day28, this.day29, this.day30, this.day31
        };
    }
    public void setHours(int[] hours) {
        int i = 0;
        for( Field field : Timesheet.class.getDeclaredFields() ) {
            if(field.getName().startsWith("day")) {
                try {
                    field.set(this, hours[i++]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getSum() {
        return Arrays.stream(this.getHours()).mapToInt(Integer::intValue).sum();
    }

    @Override
    public String toString() {
        return "Timesheet-" + this.getMonth() + "-" + this.getId();
    }
}