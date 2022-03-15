package com.tigercard.models;

import java.time.LocalDate;
import java.util.Objects;

public class DayRange {
    LocalDate from;
    LocalDate to;

    public DayRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayRange dayRange = (DayRange) o;
        return Objects.equals(from, dayRange.from) && Objects.equals(to, dayRange.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
