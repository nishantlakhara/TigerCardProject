package com.tigercard.dao.impl;

import com.tigercard.dao.JourneyDao;
import com.tigercard.domain.Fare;

import java.time.LocalDate;
import java.util.Optional;

public class DBDailyJourneyDao implements JourneyDao<Fare> {
    @Override
    public Optional<Fare> get(int id, LocalDate localDate) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }

    @Override
    public void save(Fare fare) {
        throw new UnsupportedOperationException("DBDailyJourneyDao not supported yet.");
    }
}
