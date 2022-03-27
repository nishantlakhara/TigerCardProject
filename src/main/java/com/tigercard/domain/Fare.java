package com.tigercard.domain;

import java.time.LocalDate;

public class Fare {
    private int commuterId;
    private LocalDate localDate;
    private int fare;

    public Fare(int commuterId, LocalDate localDate, int fare) {
        this.commuterId = commuterId;
        this.localDate = localDate;
        this.fare = fare;
    }

    public int getCommuterId() {
        return commuterId;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getFare() {
        return fare;
    }

}
