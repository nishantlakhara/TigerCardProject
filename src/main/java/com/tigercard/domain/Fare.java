package com.tigercard.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public void setCommuterId(int commuterId) {
        this.commuterId = commuterId;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}
