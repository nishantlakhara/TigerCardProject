package com.tigercard.calculator;

import com.tigercard.enums.CappingType;
import com.tigercard.models.Journey;

public interface FareCalculator {
    CappingType getCapping();
//    int calculate(List<Journey> journeys);

    int calculate(Journey journey, int fare);
}
