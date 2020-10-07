package com.qualityunit.model;

import java.time.LocalDate;

public class WaitingTimeline extends Line {
    private LocalDate date;
    private int waitingTime;

    public WaitingTimeline(String[] currLine, ResponseType responseType, LocalDate date, int waitingTime) {
        super(currLine, responseType);
        this.date = date;
        this.waitingTime = waitingTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", date=" + date +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
