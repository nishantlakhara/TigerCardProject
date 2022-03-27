package com.tigercard.calculator;

import com.tigercard.dao.DailyCappingDao;
import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Capping;
import com.tigercard.enums.CappingType;
import com.tigercard.models.Zone;

import java.util.Map;

public class DailyFareCalculator extends AbstractFareCalculator {

    public DailyFareCalculator(JourneyDao journeyDao, DailyCappingDao<Capping> dailyCappingDao, Map<Zone, Integer> fareCapping) {
        super(journeyDao, null, dailyCappingDao, fareCapping);
    }

    @Override
    public CappingType getCapping() {
        return CappingType.DAILY;
    }
}
