package com.tigercard.dao.impl;

import com.tigercard.dao.RateDao;
import com.tigercard.models.Zone;

import java.time.LocalDateTime;

public class DBRateDao implements RateDao<Integer> {
    @Override
    public Integer getFare(LocalDateTime localDateTime, Zone zone) {
        throw new UnsupportedOperationException("The dao is not implemented yet.");
    }
}
