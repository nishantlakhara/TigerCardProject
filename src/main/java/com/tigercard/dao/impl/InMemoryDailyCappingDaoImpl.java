package com.tigercard.dao.impl;

import com.tigercard.dao.DailyCappingDao;
import com.tigercard.domain.Capping;
import com.tigercard.models.DayRange;
import com.tigercard.utils.DateUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDailyCappingDaoImpl implements DailyCappingDao<Capping> {
    Map<Integer, Map<DayRange, Capping>> cappingMapDaily = new HashMap<>();

    @Override
    public void saveCapping(Capping capping) {
        cappingMapDaily.putIfAbsent(capping.getCommuterId(), new HashMap<>());
        DayRange dayRange = DateUtils.generateDayRange(capping.getLocalDate());
        cappingMapDaily
                .get(capping.getCommuterId())
                .put(dayRange, capping);
    }

    @Override
    public Optional<Capping> getCapping(int id, LocalDate localDate) {
        DayRange dayRange = DateUtils.generateDayRange(localDate);

        Map<DayRange, Capping> dayRangeCappingMap = cappingMapDaily.get(id);
        return Optional.ofNullable(dayRangeCappingMap != null ? dayRangeCappingMap.get(dayRange): null);
    }
}
