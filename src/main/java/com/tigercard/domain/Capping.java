package com.tigercard.domain;

import java.time.LocalDate;

public class Capping {
    private int commuterId;
    private int from;
    private int to;
    private LocalDate localDate;
    private int capping;

    public Capping(int commuterId, int from, int to, LocalDate localDate, int capping) {
        this.commuterId = commuterId;
        this.from = from;
        this.to = to;
        this.localDate = localDate;
        this.capping = capping;
    }

    public int getCommuterId() {
        return commuterId;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getCapping() {
        return capping;
    }
}
