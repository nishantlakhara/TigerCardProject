package com.tigercard.dao;

import com.tigercard.models.Zone;

import java.time.LocalDateTime;

public interface RateDao<T> {
    T getFare(LocalDateTime localDateTime, Zone zone);
}
