package com.tigercard.calculator;

import com.tigercard.dao.JourneyDao1;
import com.tigercard.domain.Fare;
import com.tigercard.models.Journey;
import com.tigercard.models.Zone;

import java.time.LocalDate;
import java.util.Optional;

public abstract class AbstractFareCalculator implements FareCalculator {
    private JourneyDao1<Fare> journeyDao;
    private FareCalculator fareCalculatorNextLevel;

    public AbstractFareCalculator(JourneyDao1<Fare> journeyDao,
                                FareCalculator fareCalculatorNextLevel) {
        this.journeyDao = journeyDao;
        this.fareCalculatorNextLevel = fareCalculatorNextLevel;
    }

    @Override
    public int calculate(Journey journey, int fare) {
        int commuterId = journey.getCommuterId();
        LocalDate localDate = journey.getLocalDateTime().toLocalDate();
        journeyDao.saveCapping(commuterId, new Zone(journey.getFrom(), journey.getTo()), localDate);

        Optional<Fare> totalFareAlreadyCharged = journeyDao.get(commuterId, localDate);
        if(!totalFareAlreadyCharged.isPresent()) {
            totalFareAlreadyCharged = Optional.of(new Fare(commuterId, localDate, 0));
        }
        Fare totalFareAlreadyChargedObj = totalFareAlreadyCharged.get();
        Integer totalFareAlreadyChargedVal = totalFareAlreadyChargedObj.getFare();

        journeyDao.save(totalFareAlreadyChargedObj);
        Integer capping = journeyDao.getCapping(commuterId, localDate);
        if(totalFareAlreadyChargedVal + fare >= capping) {
            fare = capping - totalFareAlreadyChargedVal;
            System.out.println(getCapping().name() + " limit reached " + capping + ". So fare = " + fare);
        }

        //Recursive call to next level of capping.
        if(fareCalculatorNextLevel != null) {
            fare = fareCalculatorNextLevel.calculate(journey, fare);
        }
        journeyDao.save(new Fare(commuterId, localDate, totalFareAlreadyChargedVal + fare));
        return fare;
    }
}
