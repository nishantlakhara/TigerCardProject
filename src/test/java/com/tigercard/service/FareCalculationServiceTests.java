package com.tigercard.service;

import com.tigercard.calculator.FareCalculator;
import com.tigercard.dao.InMemoryRateDao;
import com.tigercard.enums.CappingType;
import com.tigercard.factory.FactoryType;
import com.tigercard.factory.FareCalculatorProducer;
import com.tigercard.models.Journey;
import com.tigercard.utils.JourneyUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FareCalculationServiceTests {

    @Test
    public void testDailyCapping() throws URISyntaxException {
        TreeSet<Journey> journeys = readJourneys("test1.csv");

        FareCalculator fareCalculator = FareCalculatorProducer.getFareCalculatorFactory(FactoryType.IN_MEMORY)
                .getFareCalculator(CappingType.WEEKLY);

        FareCalculationService fareCalculationService = new FareCalculationService(fareCalculator,
                new InMemoryRateDao());

        int totalFare = fareCalculationService.calculateTotalFare(journeys);
        System.out.println("Total fare calculated = " + totalFare);
        Assert.assertEquals(120, totalFare);
    }

    @Test
    public void testWeeklyCapping() throws URISyntaxException {
        TreeSet<Journey> journeys = readJourneys("test2.csv");

        FareCalculator fareCalculator = FareCalculatorProducer.getFareCalculatorFactory(FactoryType.IN_MEMORY)
                .getFareCalculator(CappingType.WEEKLY);

        FareCalculationService fareCalculationService = new FareCalculationService(fareCalculator,
                new InMemoryRateDao());


        int totalFare = fareCalculationService.calculateTotalFare(journeys);
        System.out.println("Total fare calculated = " + totalFare);
        Assert.assertEquals(720, totalFare);
    }

    private TreeSet<Journey> readJourneys(String fileName) throws URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        try (Stream<String> stream = Files.lines(path)) {
            return stream
                    .skip(1)
                    .map(line -> JourneyUtils.convertToJourney(line))
                    .collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TreeSet<>();
    }
}
