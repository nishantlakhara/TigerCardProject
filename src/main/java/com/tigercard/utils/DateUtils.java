package com.tigercard.utils;

import com.tigercard.models.DayRange;
import com.tigercard.models.TimeRange;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static DayRange generateDayRange(LocalDate dt) {
        LocalDate weekStart;
        LocalDate weekEnd;
        DayOfWeek dayOfWeek = dt.getDayOfWeek();
        if(dayOfWeek.equals(DayOfWeek.MONDAY)) {
            weekStart = dt;
            weekEnd = dt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        } else if(dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            weekStart = dt.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
            weekEnd = dt;
        } else {
            weekStart = dt.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
            weekEnd = dt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        }
        DayRange dayRange = new DayRange(weekStart, weekEnd);
        return dayRange;
    }

    public static boolean isBetweenInclusive(LocalDateTime localDateTime, TimeRange range) {
        LocalTime localTime = localDateTime.toLocalTime();
        if(localTime.isAfter(range.getStartTime().minusSeconds(1))
                && localTime.isBefore(range.getEndTime().plusSeconds(1))) {
            return true;
        }
        return false;
    }
}
