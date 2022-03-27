package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao1;
import com.tigercard.enums.CappingType;

public class DailyFareCalculator extends AbstractFareCalculator {

    public DailyFareCalculator(JourneyDao1 journeyDao) {
        super(journeyDao, null);
    }

    @Override
    public CappingType getCapping() {
        return CappingType.DAILY;
    }
}
