package com.tigercard.factory;

import com.tigercard.calculator.DailyFareCalculator;
import com.tigercard.calculator.FareCalculator;
import com.tigercard.calculator.WeeklyFareCalculator;
import com.tigercard.dao.InMemoryDailyJourneyDao;
import com.tigercard.dao.InMemoryWeeklyJourneyDao;
import com.tigercard.enums.CappingType;

public class InMemoryFareCalculatorFactory extends AbstractFareCalculatorFactory {
    @Override
    public FareCalculator getFareCalculator(CappingType cappingType) {
        switch(cappingType) {
            case WEEKLY: return new WeeklyFareCalculator(
                    new InMemoryWeeklyJourneyDao(),
                    new DailyFareCalculator(new InMemoryDailyJourneyDao()));
            case DAILY: return new DailyFareCalculator(new InMemoryDailyJourneyDao());
            default: return null;
        }
    }
}
