package com.tigercard.dao;

import com.tigercard.models.Journey;

public class InMemoryMonthlyJourneyDao implements JourneyDao {


    @Override
    public void updateFare(Journey journey, int fare) {

    }

    @Override
    public int getFare(Journey journey) {
        return 0;
    }

    @Override
    public void updateCapping(Journey journey) {

    }

    @Override
    public int getCapping(Journey journey) {
        return 0;
    }
}
