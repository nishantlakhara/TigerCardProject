package com.tigercard.domain;

public class Rate {
    private int from;
    private int to;
    private boolean isPeak;
    private int rate;

    public Rate(int from, int to, boolean isPeak, int rate) {
        this.from = from;
        this.to = to;
        this.isPeak = isPeak;
        this.rate = rate;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isPeak() {
        return isPeak;
    }

    public int getRate() {
        return rate;
    }
}
