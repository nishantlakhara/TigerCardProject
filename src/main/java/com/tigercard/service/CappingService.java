package com.tigercard.service;

import com.tigercard.constants.CappingConstants;
import com.tigercard.enums.CappingType;
import com.tigercard.models.Zone;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CappingService {
    private static CappingConstants cappingConstants;
    private final Map<Zone, Integer> DAILY_FARE_CAPPING = new HashMap<>();
    private static final Map<Zone, Integer> WEEKLY_FARE_CAPPING = new HashMap<>();

    public void loadCapping() {
        DAILY_FARE_CAPPING.put(new Zone(1, 1), 100);
        DAILY_FARE_CAPPING.put(new Zone(1, 2), 120);
        DAILY_FARE_CAPPING.put(new Zone(2, 1), 120);
        DAILY_FARE_CAPPING.put(new Zone(2, 2), 80);

        WEEKLY_FARE_CAPPING.put(new Zone(1, 1), 500);
        WEEKLY_FARE_CAPPING.put(new Zone(1, 2), 600);
        WEEKLY_FARE_CAPPING.put(new Zone(2, 1), 600);
        WEEKLY_FARE_CAPPING.put(new Zone(2, 2), 400);
    }

    public Map<Zone, Integer> getCapping(CappingType cappingType) {
        switch (cappingType) {
            case DAILY: return Collections.unmodifiableMap(DAILY_FARE_CAPPING);
            case WEEKLY: return Collections.unmodifiableMap(WEEKLY_FARE_CAPPING);
            default: return new HashMap<>();
        }
    }
}
