package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao;
import com.tigercard.enums.CappingType;
import com.tigercard.models.Journey;

public class DailyFareCalculator implements FareCalculator {
    private JourneyDao journeyDao;

    public DailyFareCalculator(JourneyDao journeyDao) {
        this.journeyDao = journeyDao;
    }

    @Override
    public CappingType getCapping() {
        return CappingType.DAILY;
    }

    @Override
    public int calculate(Journey journey, int fare) {
        int dailyFare = journeyDao.getFare(journey);

        journeyDao.updateCapping(journey);
        Integer capping = journeyDao.getCapping(journey);
        if(dailyFare + fare >= capping) {
            fare = capping - dailyFare;
            System.out.println("Daily limit reached " + capping + ". So fare = " + fare);
        }
        journeyDao.updateFare(journey, dailyFare + fare);
        return fare;
    }
}
