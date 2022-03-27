package com.tigercard.factory;

import com.tigercard.calculator.DailyFareCalculator;
import com.tigercard.calculator.FareCalculator;
import com.tigercard.calculator.WeeklyFareCalculator;
import com.tigercard.dao.impl.InMemoryDailyJourneyDao1;
import com.tigercard.dao.impl.InMemoryWeeklyJourneyDao1;
import com.tigercard.enums.CappingType;

public class InMemoryFareCalculatorFactory extends AbstractFareCalculatorFactory {
    @Override
    public FareCalculator getFareCalculator(CappingType cappingType) {
        switch(cappingType) {
            case WEEKLY: return new WeeklyFareCalculator(
                    new InMemoryWeeklyJourneyDao1(),
                    new DailyFareCalculator(new InMemoryDailyJourneyDao1()));
            case DAILY: return new DailyFareCalculator(new InMemoryDailyJourneyDao1());
            default: return null;
        }
    }
}
