package com.tigercard.service;

import com.tigercard.calculator.FareCalculator;
import com.tigercard.dao.RateDao;
import com.tigercard.models.Journey;

import java.util.Iterator;
import java.util.TreeSet;

public class FareCalculationService {
    FareCalculator fareCalculator;
    RateDao rateDao;

    public FareCalculationService(FareCalculator fareCalculator,
                                  RateDao rateDao) {
        this.fareCalculator = fareCalculator;
        this.rateDao = rateDao;
    }

    public int calculateTotalFare(TreeSet<Journey> journeys) {
        int totalFare = 0;
        Iterator<Journey> itr = journeys.iterator();
        while (itr.hasNext()) {
            Journey journey = itr.next();
            System.out.println(journey);
            int fare = rateDao.getFare(journey);
            System.out.println("One time fare = " + fare);
            int fare1 = fareCalculator.calculate(journey, fare);
            System.out.println("Fare charged = " + fare1);
            System.out.println("-----------------------------");
            totalFare += fare1;
        }
        return totalFare;
    }
}
