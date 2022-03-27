package com.tigercard.transformer;

import com.tigercard.models.DayRange;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DayRangeTransformer {
    public DayRange generateDayRange(LocalDate dt) {
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
}
