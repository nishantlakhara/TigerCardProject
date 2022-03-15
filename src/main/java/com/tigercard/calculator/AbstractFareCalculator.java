package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao;
import com.tigercard.models.Journey;

public abstract class AbstractFareCalculator implements FareCalculator {
    private JourneyDao journeyDao;
    private FareCalculator fareCalculatorNextLevel;

    public AbstractFareCalculator(JourneyDao journeyDao,
                                FareCalculator fareCalculatorNextLevel) {
        this.journeyDao = journeyDao;
        this.fareCalculatorNextLevel = fareCalculatorNextLevel;
    }

    @Override
    public int calculate(Journey journey, int fare) {
        int totalFareAlreadyCharged = journeyDao.getFare(journey);

        journeyDao.updateCapping(journey);
        Integer capping = journeyDao.getCapping(journey);
        if(totalFareAlreadyCharged + fare >= capping) {
            fare = capping - totalFareAlreadyCharged;
            System.out.println(getCapping().name() + " limit reached " + capping + ". So fare = " + fare);
        }

        //Recursive call to next level of capping.
        fare = fareCalculatorNextLevel.calculate(journey, fare);
        journeyDao.updateFare(journey, totalFareAlreadyCharged + fare);
        return fare;
    }
}
