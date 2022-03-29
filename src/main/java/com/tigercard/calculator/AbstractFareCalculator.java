package com.tigercard.calculator;

import com.tigercard.dao.CappingDao;
import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Capping;
import com.tigercard.domain.Fare;
import com.tigercard.models.Journey;
import com.tigercard.models.Zone;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractFareCalculator implements FareCalculator {
    private JourneyDao<Fare> journeyDao;
    private FareCalculator fareCalculatorNextLevel;
    private CappingDao<Capping> cappingDao;
    private Map<Zone, Integer> fareCapping;

    public AbstractFareCalculator(JourneyDao<Fare> journeyDao, FareCalculator fareCalculatorNextLevel, CappingDao<Capping> cappingDao, Map<Zone, Integer> fareCapping) {
        this.journeyDao = journeyDao;
        this.fareCalculatorNextLevel = fareCalculatorNextLevel;
        this.cappingDao = cappingDao;
        this.fareCapping = fareCapping;
    }

    @Override
    public int calculate(Journey journey, int fare) throws Exception {
        int commuterId = journey.getCommuterId();
        LocalDate localDate = journey.getLocalDateTime().toLocalDate();

        saveCapping(journey);

        Fare totalFareAlreadyChargedObj = calculateTotalFareAlreadyCharged(commuterId, localDate);

        fare = updateFare(fare, commuterId, localDate, totalFareAlreadyChargedObj.getFare());

        //Recursive call to next level of capping.
        if(fareCalculatorNextLevel != null) {
            fare = fareCalculatorNextLevel.calculate(journey, fare);
        }
        journeyDao.save(new Fare(commuterId, localDate, totalFareAlreadyChargedObj.getFare() + fare));
        return fare;
    }

    private int updateFare(int fare, int commuterId, LocalDate localDate, Integer totalFareAlreadyChargedVal) throws Exception {
        Optional<Capping> capping = cappingDao.getCapping(commuterId, localDate);
        int cappingVal = capping.get().getCapping();
        if(totalFareAlreadyChargedVal + fare >= cappingVal) {
            fare = cappingVal - totalFareAlreadyChargedVal;
            System.out.println(getCapping().name() + " limit reached " + capping + ". So fare = " + fare);
        }
        return fare;
    }

    private Fare calculateTotalFareAlreadyCharged(int commuterId, LocalDate localDate) throws Exception {
        Optional<Fare> totalFareAlreadyCharged = journeyDao.get(commuterId, localDate);
        if(!totalFareAlreadyCharged.isPresent()) {
            totalFareAlreadyCharged = Optional.of(new Fare(commuterId, localDate, 0));
        }
        Fare totalFareAlreadyChargedObj = totalFareAlreadyCharged.get();
        journeyDao.save(totalFareAlreadyChargedObj);
        return totalFareAlreadyChargedObj;
    }

    private void saveCapping(Journey journey) throws Exception {
        Optional<Capping> capping = cappingDao.getCapping(journey.getCommuterId(), journey.getLocalDateTime().toLocalDate());

        Capping cappingObj;
        int cappingValue = 0;
        if(capping.isPresent()) {
            cappingObj = capping.get();
            cappingValue = cappingObj.getCapping();
        } else {
            cappingObj = new Capping(journey.getCommuterId(), journey.getFrom(), journey.getTo(), journey.getLocalDateTime().toLocalDate(), 0);
        }

        int maxCapping = Math.max(fareCapping.get(new Zone(journey.getFrom(), journey.getTo())), cappingValue);

        cappingDao.saveCapping(createCapping(cappingObj, maxCapping));
    }

    private Capping createCapping(Capping capping, int max) {
        return new Capping(capping.getCommuterId(), capping.getFrom(), capping.getTo(), capping.getLocalDate(), max);
    }
}
