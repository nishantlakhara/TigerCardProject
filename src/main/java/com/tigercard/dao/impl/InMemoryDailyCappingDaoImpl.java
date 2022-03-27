package com.tigercard.dao.impl;

import com.tigercard.dao.DailyCappingDao;
import com.tigercard.domain.Capping;
import com.tigercard.models.DayRange;
import com.tigercard.transformer.DayRangeTransformer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDailyCappingDaoImpl implements DailyCappingDao<Capping> {
    Map<Integer, Map<DayRange, Capping>> cappingMapDaily;
    DayRangeTransformer dayRangeTransformer;

    public InMemoryDailyCappingDaoImpl() {
        cappingMapDaily = new HashMap<>();
        dayRangeTransformer = new DayRangeTransformer();
    }

    @Override
    public void saveCapping(Capping capping) {
        cappingMapDaily.putIfAbsent(capping.getCommuterId(), new HashMap<>());
        DayRange dayRange = dayRangeTransformer.generateDayRange(capping.getLocalDate());
        cappingMapDaily
                .get(capping.getCommuterId())
                .put(dayRange, capping);
    }

    @Override
    public Optional<Capping> getCapping(int id, LocalDate localDate) {
        DayRange dayRange = dayRangeTransformer.generateDayRange(localDate);

        Map<DayRange, Capping> dayRangeCappingMap = cappingMapDaily.get(id);
        return Optional.ofNullable(dayRangeCappingMap != null ? dayRangeCappingMap.get(dayRange): null);
    }
}
