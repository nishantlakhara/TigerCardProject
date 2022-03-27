package com.tigercard.factory;

import com.tigercard.calculator.DailyFareCalculator;
import com.tigercard.calculator.FareCalculator;
import com.tigercard.calculator.WeeklyFareCalculator;
import com.tigercard.constants.CappingConstants;
import com.tigercard.dao.impl.InMemoryDailyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryDailyJourneyDao;
import com.tigercard.dao.impl.InMemoryWeeklyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryWeeklyJourneyDao;
import com.tigercard.enums.CappingType;

public class InMemoryFareCalculatorFactory extends AbstractFareCalculatorFactory {
    @Override
    public FareCalculator getFareCalculator(CappingType cappingType) {
        switch(cappingType) {
            case WEEKLY: return new WeeklyFareCalculator(
                    new InMemoryWeeklyJourneyDao(),
                    new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                        new InMemoryDailyCappingDaoImpl(),
                            CappingConstants.getInstance().getCapping(CappingType.DAILY)
                    ),
                    new InMemoryWeeklyCappingDaoImpl(),
                    CappingConstants.getInstance().getCapping(CappingType.WEEKLY)
                    );
            case DAILY: return new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                                        new InMemoryDailyCappingDaoImpl(),
                                        CappingConstants.getInstance().getCapping(CappingType.DAILY)
                    );
            default: return null;
        }
    }
}
