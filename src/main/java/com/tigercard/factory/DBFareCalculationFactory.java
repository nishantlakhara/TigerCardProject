package com.tigercard.factory;

import com.tigercard.calculator.FareCalculator;
import com.tigercard.enums.CappingType;

public class DBFareCalculationFactory extends AbstractFareCalculatorFactory {
    @Override
    public FareCalculator getFareCalculator(CappingType cappingType) {
        throw new UnsupportedOperationException("This factory is not implemented yet.");
    }
}
