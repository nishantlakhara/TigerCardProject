package com.tigercard.service;

import com.tigercard.cache.RateCache;
import com.tigercard.calculator.FareCalculator;
import com.tigercard.converter.JourneyConverter;
import com.tigercard.enums.CappingType;
import com.tigercard.factory.FactoryType;
import com.tigercard.factory.FareCalculatorProducer;
import com.tigercard.loader.impl.RateLoaderImpl;
import com.tigercard.models.Journey;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FareCalculationServiceTests {
    RateService rateService;
    JourneyConverter converter = new JourneyConverter();

    public FareCalculationServiceTests() throws Exception {
        RateCache rateCache = new RateCache(new RateLoaderImpl("testZonePeakHours.csv",
                "testZoneOffPeakHours.csv",
                "testPeakRanges.csv",
                "testPeakRangesWeekend.csv"));
        rateCache.load();
        rateService = new RateService(
                rateCache);
    }

    @Test
    public void testDailyCapping() throws Exception {
        List<Journey> journeys = readJourneys("test1.csv");

        FareCalculator fareCalculator = FareCalculatorProducer.getFareCalculatorFactory(FactoryType.IN_MEMORY)
                .getFareCalculator(CappingType.WEEKLY);

        FareCalculationService fareCalculationService = new FareCalculationService(fareCalculator,
                rateService);

        int totalFare = fareCalculationService.calculateTotalFare(journeys);
        System.out.println("Total fare calculated = " + totalFare);
        Assert.assertEquals(120, totalFare);
    }

    @Test
    public void testWeeklyCapping() throws Exception {
        List<Journey> journeys = readJourneys("test2.csv");

        FareCalculator fareCalculator = FareCalculatorProducer.getFareCalculatorFactory(FactoryType.IN_MEMORY)
                .getFareCalculator(CappingType.WEEKLY);

        FareCalculationService fareCalculationService = new FareCalculationService(fareCalculator,
                rateService);


        int totalFare = fareCalculationService.calculateTotalFare(journeys);
        System.out.println("Total fare calculated = " + totalFare);
        Assert.assertEquals(720, totalFare);
    }

    private List<Journey> readJourneys(String fileName) throws URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        try (Stream<String> stream = Files.lines(path)) {
            return stream
                    .skip(1)
                    .map(line -> converter.convertToJourney(line))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
