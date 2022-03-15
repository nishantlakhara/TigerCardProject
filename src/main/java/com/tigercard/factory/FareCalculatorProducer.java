package com.tigercard.factory;

public class FareCalculatorProducer {
    public static AbstractFareCalculatorFactory getFareCalculatorFactory(FactoryType factoryType) {
        switch (factoryType) {
            case IN_MEMORY: return new InMemoryFareCalculatorFactory();
            case DB: return new DBFareCalculationFactory();
            default: return null;
        }
    }
}
