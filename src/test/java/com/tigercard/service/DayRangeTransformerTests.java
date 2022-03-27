package com.tigercard.service;

import com.tigercard.models.DayRange;
import com.tigercard.transformer.DayRangeTransformer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DayRangeTransformerTests {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DayRangeTransformer dayRangeTransformer = new DayRangeTransformer();

    @Test
    public void testGenerateWeekDetails() {
        //Monday
        DayRange range = dayRangeTransformer.generateDayRange(LocalDate.of(2022,02,14));
        Assert.assertEquals(range.getFrom(), LocalDate.of(2022,02,14));
        Assert.assertEquals(range.getTo(), LocalDate.of(2022,02,20));

        //Sunday
        range = dayRangeTransformer.generateDayRange(LocalDate.of(2022,02,27));
        Assert.assertEquals(range.getFrom(), LocalDate.of(2022,02,21));
        Assert.assertEquals(range.getTo(), LocalDate.of(2022,02,27));

        //Any day between
        range = dayRangeTransformer.generateDayRange(LocalDate.of(2022,02,25));
        Assert.assertEquals(range.getFrom(), LocalDate.of(2022,02,21));
        Assert.assertEquals(range.getTo(), LocalDate.of(2022,02,27));
    }

//    @Test
//    public void testIsBetweenInclusive() {
//        LocalDateTime localDateTime = LocalDateTime.of(
//                LocalDate.of(2022, 02, 22),
//                LocalTime.of(10,30)
//        );
//
//        boolean betweenInclusive = DateUtils.isBetweenInclusive(localDateTime, new TimeRange(LocalTime.of(10, 30),
//                LocalTime.of(11, 0)));
//        Assert.assertEquals(true, betweenInclusive);
//
//        betweenInclusive = DateUtils.isBetweenInclusive(localDateTime, new TimeRange(LocalTime.of(10, 31),
//                LocalTime.of(15, 0)));
//        Assert.assertEquals(false, betweenInclusive);
//
//        betweenInclusive = DateUtils.isBetweenInclusive(localDateTime, new TimeRange(LocalTime.of(10, 0),
//                LocalTime.of(10, 30)));
//        Assert.assertEquals(true, betweenInclusive);
//
//    }

//    @Test
//    public void testLocalDateTimeFormat() {
//        String str = "1986-04-08 12:30";
//        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
//        System.out.println(dateTime.getDayOfWeek());
//        System.out.println(dateTime);
//    }
//
//    @Test
//    public void testLocalTime() {
//        System.out.println(isBetween(LocalTime.parse( "00:00:00" ) ));
//        System.out.println(isBetween(LocalTime.parse( "04:00:00" ) ));
//        System.out.println(isBetween(LocalTime.parse( "01:00:00" ) ));
//    }
//
//    private boolean isBetween(LocalTime target) {
//        return target.isAfter(LocalTime.parse("00:00:00"))
//                &&
//                target.isBefore(LocalTime.parse("04:00:00"));
//    }
}
