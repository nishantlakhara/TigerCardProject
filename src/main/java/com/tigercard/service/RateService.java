package com.tigercard.service;

import com.tigercard.cache.RateCache;
import com.tigercard.domain.Rate;
import com.tigercard.models.TimeRange;
import com.tigercard.models.Zone;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class RateService {
    private RateCache rateCache;

    public RateService(RateCache rateCache) {
        this.rateCache = rateCache;
    }

    public Rate getFare(LocalDateTime localDateTime, Zone zone) {
        boolean isPeak = checkForRange(localDateTime);
        int rate;
        if(isPeak) {
            rate =  rateCache.getZoneToPeakMap().get(zone);
        } else {
            rate = rateCache.getZoneToOffPeakMap().get(zone);
        }
        return new Rate(zone.getFrom(), zone.getTo(), isPeak, rate);
    }

    private boolean checkForRange(LocalDateTime localDateTime) {
        List<TimeRange> ranges = getTimeRanges(localDateTime);
        for(TimeRange range: ranges) {
            if (isBetweenInclusive(localDateTime, range)) return true;
        }
        return false;
    }

    private List<TimeRange> getTimeRanges(LocalDateTime localDateTime) {
        List<TimeRange> ranges = null;
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if(dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            ranges = rateCache.getPeakRangesWeekend();
        } else {
            ranges = rateCache.getPeakRanges();
        }
        return ranges;
    }

    public boolean isBetweenInclusive(LocalDateTime localDateTime, TimeRange range) {
        LocalTime localTime = localDateTime.toLocalTime();
        if(localTime.isAfter(range.getStartTime().minusSeconds(1))
                && localTime.isBefore(range.getEndTime().plusSeconds(1))) {
            return true;
        }
        return false;
    }
}
