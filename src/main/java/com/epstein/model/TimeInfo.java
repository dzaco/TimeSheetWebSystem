package com.epstein.model;

import java.time.Duration;
import java.time.LocalTime;

public class TimeInfo {
    private LocalTime time;

    public TimeInfo() {
        this.time = LocalTime.now();
    }
    public TimeInfo(int hour, int minute) {
        this.time = LocalTime.of(hour, minute);
    }
    public TimeInfo(LocalTime time) {
        this.time = time;
    }


    public int getHour() { return this.time.getHour(); }
    public int getMinute() { return this.time.getMinute(); }

    @Override
    public String toString() {
        return this.getHour() + ":" + this.getMinute();
    }

    public LocalTime toLocalTime() {
        return this.time;
    }

    public Duration duration(TimeInfo other) {
        return Duration.between( this.toLocalTime() , other.toLocalTime() );
    }
}
