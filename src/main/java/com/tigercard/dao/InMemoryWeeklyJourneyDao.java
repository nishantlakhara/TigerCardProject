package com.tigercard.dao;

import com.tigercard.models.DayRange;
import com.tigercard.models.Journey;
import com.tigercard.models.Zone;
import com.tigercard.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

public class InMemoryWeeklyJourneyDao implements JourneyDao {
    Map<Integer, Map<DayRange, Integer>> fareMapWeekly = new HashMap<>();
    Map<Integer, Map<DayRange, Integer>> cappingMapWeekly = new HashMap<>();

    private static final Map<Zone, Integer> WEEKLY_FARE_CAPPING = new HashMap<>();

    /**
     * Note : This information may be made dynamic by fetching it from database.
     */
    static {
        WEEKLY_FARE_CAPPING.put(new Zone(1, 1), 500);
        WEEKLY_FARE_CAPPING.put(new Zone(1, 2), 600);
        WEEKLY_FARE_CAPPING.put(new Zone(2, 1), 600);
        WEEKLY_FARE_CAPPING.put(new Zone(2, 2), 400);
    }

    @Override
    public void updateFare(Journey journey, int fare) {
        fareMapWeekly.putIfAbsent(journey.getCommuterId(), new HashMap<>());
        fareMapWeekly
                .get(journey.getCommuterId())
                .put(DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate()), fare);
    }

    @Override
    public int getFare(Journey journey) {
        //return 0;
        DayRange dayRange = DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate());

        return fareMapWeekly.getOrDefault(journey.getCommuterId(), new HashMap<>())
                .getOrDefault(dayRange, 0);
    }

    @Override
    public void updateCapping(Journey journey) {
        cappingMapWeekly.putIfAbsent(journey.getCommuterId(), new HashMap<>());
        Zone zone = new Zone(journey.getFrom(), journey.getTo());

        DayRange dayRange = DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate());
        int maxCap = Math.max(WEEKLY_FARE_CAPPING.get(zone),
                cappingMapWeekly.get(journey.getCommuterId())
                        .getOrDefault(dayRange, 0));
        cappingMapWeekly
                .get(journey.getCommuterId())
                .put(dayRange, maxCap);
    }

    @Override
    public int getCapping(Journey journey) {
        DayRange dayRange = DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate());

        return cappingMapWeekly.get(journey.getCommuterId())
                .get(dayRange);
    }
}
