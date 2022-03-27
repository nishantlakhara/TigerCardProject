package com.tigercard.factory;

import com.tigercard.cache.CappingCache;
import com.tigercard.calculator.DailyFareCalculator;
import com.tigercard.calculator.FareCalculator;
import com.tigercard.calculator.WeeklyFareCalculator;
import com.tigercard.dao.impl.InMemoryDailyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryDailyJourneyDao;
import com.tigercard.dao.impl.InMemoryWeeklyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryWeeklyJourneyDao;
import com.tigercard.enums.CappingType;
import com.tigercard.loader.impl.FileFareCappingLoaderImpl;
import com.tigercard.service.CappingService;

public class InMemoryFareCalculatorFactory extends AbstractFareCalculatorFactory {
    @Override
    public FareCalculator getFareCalculator(CappingType cappingType) {
        CappingService cappingService = new CappingService(
                new CappingCache(
                        new FileFareCappingLoaderImpl("fareCapping.csv")
                )
        );

        switch(cappingType) {
            case WEEKLY: return new WeeklyFareCalculator(
                    new InMemoryWeeklyJourneyDao(),
                    new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                        new InMemoryDailyCappingDaoImpl(),
                            cappingService.getCapping(CappingType.DAILY)
                    ),
                    new InMemoryWeeklyCappingDaoImpl(),
                            cappingService.getCapping(CappingType.WEEKLY)
                    );
            case DAILY: return new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                                        new InMemoryDailyCappingDaoImpl(),
                                        cappingService.getCapping(CappingType.DAILY)
                    );
            default: return null;
        }
    }
}
