package com.tigercard.dao.impl;

import com.tigercard.dao.JourneyDao;
import com.tigercard.models.Journey;

public class DBDailyJourneyDao implements JourneyDao {
    @Override
    public void updateFare(Journey journey, int fare) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }

    @Override
    public int getFare(Journey journey) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }

    @Override
    public void updateCapping(Journey journey) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }

    @Override
    public int getCapping(Journey journey) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }
}
