package com.tigercard.factory;

import com.tigercard.calculator.FareCalculator;
import com.tigercard.enums.CappingType;

public abstract class AbstractFareCalculatorFactory {
     public abstract FareCalculator getFareCalculator(CappingType cappingType);
}
