package com.tigercard.dao;

import com.tigercard.models.Journey;

public interface RateDao {
    int getFare(Journey journey);
}
