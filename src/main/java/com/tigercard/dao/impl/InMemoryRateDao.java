package com.tigercard.dao.impl;

import com.tigercard.dao.RateDao;
import com.tigercard.domain.Rate;
import com.tigercard.models.TimeRange;
import com.tigercard.models.Zone;
import com.tigercard.utils.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ○ Monday - Friday
 * ■ 07:00 - 10:30, 17:00 - 20:00
 * ○ Saturday - Sunday
 * ■ 09:00 - 11:00, 18:00 - 22:00
 */
public class InMemoryRateDao implements RateDao<Rate> {
    private static final Map<Zone, Integer> ZONE_TO_PEAK_HOUR_MAP = new HashMap<>();
    private static final Map<Zone, Integer> ZONE_TO_OFF_PEAK_MAP = new HashMap<>();
    private static final List<TimeRange> PEAK_RANGES = new ArrayList<>();
    private static final List<TimeRange> PEAK_RANGES_WEEKEND = new ArrayList<>();

    /**
     * Note : This information may be made dynamic by fetching it from database.
     */
    static {
        ZONE_TO_PEAK_HOUR_MAP.put(new Zone(1,1), 30);
        ZONE_TO_PEAK_HOUR_MAP.put(new Zone(1,2), 35);
        ZONE_TO_PEAK_HOUR_MAP.put(new Zone(2,1), 35);
        ZONE_TO_PEAK_HOUR_MAP.put(new Zone(2,2), 25);

        ZONE_TO_OFF_PEAK_MAP.put(new Zone(1,1), 25);
        ZONE_TO_OFF_PEAK_MAP.put(new Zone(1,2), 30);
        ZONE_TO_OFF_PEAK_MAP.put(new Zone(2,1), 30);
        ZONE_TO_OFF_PEAK_MAP.put(new Zone(2,2), 20);

        PEAK_RANGES.add(new TimeRange(LocalTime.of(07,0), LocalTime.of(10, 30)));
        PEAK_RANGES.add(new TimeRange(LocalTime.of(17,0), LocalTime.of(20, 0)));

        PEAK_RANGES_WEEKEND.add(new TimeRange(LocalTime.of(9,0), LocalTime.of(11, 0)));
        PEAK_RANGES_WEEKEND.add(new TimeRange(LocalTime.of(18,0), LocalTime.of(22, 0)));
    }

    @Override
    public Rate getFare(LocalDateTime localDateTime, Zone zone) {
        boolean isPeak = checkForRange(localDateTime);
        int rate;
        if(isPeak) {
            rate =  ZONE_TO_PEAK_HOUR_MAP.get(zone);
        } else {
            rate = ZONE_TO_OFF_PEAK_MAP.get(zone);
        }
        return new Rate(zone.getFrom(), zone.getTo(), isPeak, rate);
    }

    private boolean checkForRange(LocalDateTime localDateTime) {
        List<TimeRange> ranges = getTimeRanges(localDateTime);
        for(TimeRange range: ranges) {
            if (DateUtils.isBetweenInclusive(localDateTime, range)) return true;
        }
        return false;
    }

    private List<TimeRange> getTimeRanges(LocalDateTime localDateTime) {
        List<TimeRange> ranges = null;
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if(dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            ranges = PEAK_RANGES_WEEKEND;
        } else {
            ranges = PEAK_RANGES;
        }
        return ranges;
    }
}
