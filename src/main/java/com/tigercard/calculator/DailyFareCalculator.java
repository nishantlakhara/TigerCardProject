package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao;
import com.tigercard.dao.JourneyDao1;
import com.tigercard.domain.Fare;
import com.tigercard.enums.CappingType;
import com.tigercard.models.Journey;

import java.util.Optional;

public class DailyFareCalculator extends AbstractFareCalculator {

    public DailyFareCalculator(JourneyDao1 journeyDao) {
        super(journeyDao, null);
    }

    @Override
    public CappingType getCapping() {
        return CappingType.DAILY;
    }
}
