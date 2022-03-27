package com.tigercard.dao.impl;

import com.tigercard.constants.CappingConstants;
import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Fare;
import com.tigercard.enums.CappingType;
import com.tigercard.models.DayRange;
import com.tigercard.models.Zone;
import com.tigercard.utils.DateUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryWeeklyJourneyDao implements JourneyDao<Fare> {
    Map<Integer, Map<DayRange, Fare>> fareMapWeekly = new HashMap<>();

    @Override
    public Optional<Fare> get(int id, LocalDate localDate) {
        DayRange dayRange = DateUtils.generateDayRange(localDate);

        return Optional.ofNullable(fareMapWeekly.getOrDefault(id, new HashMap<>())
                .getOrDefault(dayRange, null));
    }

    @Override
    public void save(Fare fare) {
        fareMapWeekly.putIfAbsent(fare.getCommuterId(), new HashMap<>());
        fareMapWeekly
                .get(fare.getCommuterId())
                .put(DateUtils.generateDayRange(fare.getLocalDate()), fare);
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
