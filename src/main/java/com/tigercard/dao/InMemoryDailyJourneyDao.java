package com.tigercard.dao;

import com.tigercard.models.DayRange;
import com.tigercard.models.Journey;
import com.tigercard.models.Zone;
import com.tigercard.utils.DateUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDailyJourneyDao implements JourneyDao {
    Map<Integer, Map<LocalDate, Integer>> fareMapDaily = new HashMap<>();
    Map<Integer, Map<DayRange, Integer>> cappingMapDaily = new HashMap<>();
    private static final Map<Zone, Integer> DAILY_FARE_CAPPING = new HashMap<>();

    /**
     * Note : This information may be made dynamic by fetching it from database.
     */
    static {
        DAILY_FARE_CAPPING.put(new Zone(1, 1), 100);
        DAILY_FARE_CAPPING.put(new Zone(1, 2), 120);
        DAILY_FARE_CAPPING.put(new Zone(2, 1), 120);
        DAILY_FARE_CAPPING.put(new Zone(2, 2), 80);
    }

    @Override
    public void updateFare(Journey journey, int fare) {
        fareMapDaily.putIfAbsent(journey.getCommuterId(), new HashMap<>());
        fareMapDaily
                .get(journey.getCommuterId())
                .put(journey.getLocalDateTime().toLocalDate(), fare);
    }

    @Override
    public int getFare(Journey journey) {
        return fareMapDaily
                .getOrDefault(journey.getCommuterId(), new HashMap<>())
                .getOrDefault(journey.getLocalDateTime().toLocalDate(), 0);
    }

    @Override
    public void updateCapping(Journey journey) {
        cappingMapDaily.putIfAbsent(journey.getCommuterId(), new HashMap<>());
        Zone zone = new Zone(journey.getFrom(), journey.getTo());
        DayRange dayRange = DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate());
        cappingMapDaily
                .get(journey.getCommuterId())
                .put(dayRange,
                        Math.max(DAILY_FARE_CAPPING.get(zone),
                                cappingMapDaily.get(journey.getCommuterId())
                                        .getOrDefault(dayRange, 0)
                        ));
    }

    @Override
    public int getCapping(Journey journey) {
        DayRange dayRange = DateUtils.generateDayRange(journey.getLocalDateTime().toLocalDate());

        return cappingMapDaily.get(journey.getCommuterId())
                .get(dayRange);
    }
}
