package com.tigercard.dao.impl;

import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Fare;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDailyJourneyDao implements JourneyDao<Fare> {
    Map<Integer, Map<LocalDate, Fare>> fareMapDaily = new HashMap<>();

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

//    @Override
//    public void saveCapping(int id, Zone zone, LocalDate localDate) {
//
//    }
//
//    @Override
//    public int getCapping(int id, LocalDate localDate) {
//
//    }

//    @Override
//    public void update(Fare fare, String[] params) {
//        fareMapDaily.putIfAbsent(fare.getCommuterId(), new HashMap<>());
//        fareMapDaily
//                .get(fare.getCommuterId())
//                .put(fare.getLocalDateTime().toLocalDate(), fare.getFare());
//    }
}
