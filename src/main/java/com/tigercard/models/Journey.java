package com.tigercard.models;

import java.time.LocalDateTime;

public class Journey implements Comparable<Journey> {
    private int commuterId;
    private LocalDateTime localDateTime;
    private int from;
    private int to;

    public Journey(int commuterId, LocalDateTime localDateTime, int from, int to) {
        this.commuterId = commuterId;
        this.localDateTime = localDateTime;
        this.from = from;
        this.to = to;
    }

    public int getCommuterId() {
        return commuterId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "commuterId=" + commuterId +
                ", localDateTime=" + localDateTime +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public int compareTo(Journey o) {
        return this.localDateTime.compareTo(o.localDateTime);
    }
}
