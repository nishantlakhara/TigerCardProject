package com.tigercard.service;

import com.tigercard.calculator.FareCalculator;
import com.tigercard.exception.FareCalcException;
import com.tigercard.models.Journey;
import com.tigercard.models.Zone;

import java.util.Iterator;
import java.util.List;

public class FareCalculationService {
    FareCalculator fareCalculator;
    RateService rateService;

    public FareCalculationService(FareCalculator fareCalculator,
                                  RateService rateService) {
        this.fareCalculator = fareCalculator;
        this.rateService = rateService;
    }

    public int calculateTotalFare(List<Journey> journeys) throws Exception {
        try {
            int totalFare = 0;
            Iterator<Journey> itr = journeys.iterator();
            while (itr.hasNext()) {
                Journey journey = itr.next();
                System.out.println(journey);
                int fare = rateService.getFare(journey.getLocalDateTime(), new Zone(journey.getFrom(), journey.getTo())).getRate();
                System.out.println("One time fare = " + fare);
                int fare1 = fareCalculator.calculate(journey, fare);
                System.out.println("Fare charged = " + fare1);
                System.out.println("-----------------------------");
                totalFare += fare1;
            }
            return totalFare;
        } catch(Exception e) {
            throw new FareCalcException(e.getMessage());
        }
    }
}
