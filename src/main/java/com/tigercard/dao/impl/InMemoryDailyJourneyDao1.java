package com.tigercard.dao.impl;

import com.tigercard.constants.CappingConstants;
import com.tigercard.dao.JourneyDao1;
import com.tigercard.domain.Fare;
import com.tigercard.enums.CappingType;
import com.tigercard.models.DayRange;
import com.tigercard.models.Zone;
import com.tigercard.utils.DateUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDailyJourneyDao1 implements JourneyDao1<Fare> {
    Map<Integer, Map<LocalDate, Fare>> fareMapDaily = new HashMap<>();
    Map<Integer, Map<DayRange, Integer>> cappingMapDaily = new HashMap<>();
    Map<Zone, Integer> dailyCapping = CappingConstants.getInstance().getCapping(CappingType.DAILY);

    @Override
    public Optional<Fare> get(int id, LocalDate localDate) {
         return Optional.ofNullable(fareMapDaily
                .getOrDefault(id, new HashMap<>())
                .getOrDefault(localDate, null));
    }

    @Override
    public void save(Fare fare) {
        fareMapDaily.putIfAbsent(fare.getCommuterId(), new HashMap<>());
        fareMapDaily
                .get(fare.getCommuterId())
                .put(fare.getLocalDate(), fare);
    }

    @Override
    public void saveCapping(int id, Zone zone, LocalDate localDate) {
        cappingMapDaily.putIfAbsent(id, new HashMap<>());
        DayRange dayRange = DateUtils.generateDayRange(localDate);
        cappingMapDaily
                .get(id)
                .put(dayRange,
                        Math.max(dailyCapping.get(zone),
                                cappingMapDaily.get(id)
                                        .getOrDefault(dayRange, 0)
                        ));
    }

    @Override
    public int getCapping(int id, LocalDate localDate) {
        DayRange dayRange = DateUtils.generateDayRange(localDate);

        return cappingMapDaily.get(id)
                .get(dayRange);
    }

//    @Override
//    public void update(Fare fare, String[] params) {
//        fareMapDaily.putIfAbsent(fare.getCommuterId(), new HashMap<>());
//        fareMapDaily
//                .get(fare.getCommuterId())
//                .put(fare.getLocalDateTime().toLocalDate(), fare.getFare());
//    }
}
