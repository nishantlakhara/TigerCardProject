package com.tigercard.utils;

import com.tigercard.models.Journey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JourneyUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Journey convertToJourney(String line) {
        String[] split = line.split(",");
        return new Journey(
                Integer.parseInt(split[0]),
                LocalDateTime.parse(split[1], formatter),
                Integer.parseInt(split[2]),
                Integer.parseInt(split[3])
        );
    }
}
