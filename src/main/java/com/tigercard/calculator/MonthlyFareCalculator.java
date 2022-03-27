package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao1;
import com.tigercard.enums.CappingType;

public class MonthlyFareCalculator extends AbstractFareCalculator {
    public MonthlyFareCalculator(JourneyDao1 journeyDao, FareCalculator fareCalculatorNextLevel) {
        super(journeyDao, fareCalculatorNextLevel);
    }

    @Override
    public CappingType getCapping() {
        return CappingType.MONTHLY;
    }
}
