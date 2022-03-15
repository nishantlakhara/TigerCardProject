package com.tigercard.dao;

import com.tigercard.models.Journey;

public interface JourneyDao {

    void updateFare(Journey journey, int fare);

    int getFare(Journey journey);

    void updateCapping(Journey journey);

    int getCapping(Journey journey);
}
