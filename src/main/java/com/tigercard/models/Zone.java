package com.tigercard.models;

import java.util.Objects;

public class Zone {
    private int from;
    private int to;

    public Zone(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return from == zone.from && to == zone.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
