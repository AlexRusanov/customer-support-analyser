package com.qualityunit.model;

import java.time.LocalDate;

public class QueryLine extends Line {
    private LocalDate start;
    private LocalDate end;

    public QueryLine(String[] currLine, ResponseType responseType, LocalDate start, LocalDate end) {
        super(currLine, responseType);
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return super.toString() +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
