package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao;
import com.tigercard.enums.CappingType;

public class MonthlyFareCalculator extends AbstractFareCalculator {
    public MonthlyFareCalculator(JourneyDao journeyDao, FareCalculator fareCalculatorNextLevel) {
        super(journeyDao, fareCalculatorNextLevel);
    }

    @Override
    public CappingType getCapping() {
        return CappingType.MONTHLY;
    }
}
