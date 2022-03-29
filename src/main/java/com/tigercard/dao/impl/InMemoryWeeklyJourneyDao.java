package com.tigercard.dao.impl;

import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Fare;
import com.tigercard.models.DayRange;
import com.tigercard.transformer.DayRangeTransformer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryWeeklyJourneyDao implements JourneyDao<Fare> {
    Map<Integer, Map<DayRange, Fare>> fareMapWeekly;
    DayRangeTransformer dayRangeTransformer;

    public InMemoryWeeklyJourneyDao() {
        this.fareMapWeekly = new HashMap<>();
        dayRangeTransformer = new DayRangeTransformer();
    }

    @Override
    public Optional<Fare> get(int id, LocalDate localDate) throws Exception {
        DayRange dayRange = dayRangeTransformer.generateDayRange(localDate);

        return Optional.ofNullable(fareMapWeekly.getOrDefault(id, new HashMap<>())
                .getOrDefault(dayRange, null));
    }

    @Override
    public void save(Fare fare) throws Exception {
        fareMapWeekly.putIfAbsent(fare.getCommuterId(), new HashMap<>());
        fareMapWeekly
                .get(fare.getCommuterId())
                .put(dayRangeTransformer.generateDayRange(fare.getLocalDate()), fare);
    }
//
//    @Override
//    public void saveCapping(int id, Zone zone, LocalDate localDate) {
//        cappingMapWeekly.putIfAbsent(id, new HashMap<>());
//
//        DayRange dayRange = DateUtils.generateDayRange(localDate);
//        int maxCap = Math.max(weeklyCapping.get(zone),
//                cappingMapWeekly.get(id)
//                        .getOrDefault(dayRange, 0));
//        cappingMapWeekly
//                .get(id)
//                .put(dayRange, maxCap);
//    }
//
//    @Override
//    public int getCapping(int id, LocalDate localDate) {
//        DayRange dayRange = DateUtils.generateDayRange(localDate);
//
//        return cappingMapWeekly.get(id)
//                .get(dayRange);
//    }
}
