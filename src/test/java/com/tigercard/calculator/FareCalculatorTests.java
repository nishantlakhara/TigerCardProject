package com.tigercard.calculator;

import com.tigercard.cache.CappingCache;
import com.tigercard.converter.JourneyConverter;
import com.tigercard.dao.impl.InMemoryDailyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryDailyJourneyDao;
import com.tigercard.dao.impl.InMemoryWeeklyCappingDaoImpl;
import com.tigercard.dao.impl.InMemoryWeeklyJourneyDao;
import com.tigercard.enums.CappingType;
import com.tigercard.loader.impl.FileFareCappingLoaderImpl;
import com.tigercard.models.Journey;
import com.tigercard.service.CappingService;
import org.junit.Assert;
import org.junit.Test;

public class FareCalculatorTests {

    CappingService cappingService;
    JourneyConverter converter = new JourneyConverter();
    
    public FareCalculatorTests() throws Exception {
        CappingCache cappingCache = new CappingCache(new FileFareCappingLoaderImpl("testFareCapping.csv"));
        cappingCache.loadCapping();
        cappingService = new CappingService(
                cappingCache);
        
        
    }

    @Test
    public void testCalculateWeekly() throws Exception {
        WeeklyFareCalculator weeklyFareCalculator = new WeeklyFareCalculator(
                new InMemoryWeeklyJourneyDao(),
                new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                        new InMemoryDailyCappingDaoImpl(),
                        cappingService.getCapping(CappingType.DAILY)
                ),
                new InMemoryWeeklyCappingDaoImpl(),
                cappingService.getCapping(CappingType.WEEKLY)
        );

        //120 daily cap
        Journey journey = converter.convertToJourney("1,2022-02-21 10:20,2,1");
        int out = weeklyFareCalculator.calculate(journey, 100);
        Assert.assertEquals(100, out);

        out = weeklyFareCalculator.calculate(journey, 50);
        Assert.assertEquals(20, out);

        out = weeklyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(0, out);

        //120 daily cap
        journey = converter.convertToJourney("1,2022-02-22 10:20,2,1");

        out = weeklyFareCalculator.calculate(journey, 130);
        Assert.assertEquals(120, out);

        out = weeklyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(0, out);

        //120 daily cap
        journey = converter.convertToJourney("1,2022-02-23 10:20,2,1");

        out = weeklyFareCalculator.calculate(journey, 130);
        Assert.assertEquals(120, out);

        out = weeklyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(0, out);

        //120 daily cap
        journey = converter.convertToJourney("1,2022-02-25 10:20,2,1");

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(70, out);

        out = weeklyFareCalculator.calculate(journey, 80);
        Assert.assertEquals(50, out);

        //70 daily
        journey = converter.convertToJourney("1,2022-02-26 10:20,2,1");

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(70, out);

        //weekly breached
        journey = converter.convertToJourney("1,2022-02-26 10:20,2,1");

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(50, out);

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(0, out);

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(0, out);

        //weekly breached
        journey = converter.convertToJourney("1,2022-02-28 10:20,2,1");

        //New week started
        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(70, out);

        out = weeklyFareCalculator.calculate(journey, 70);
        Assert.assertEquals(50, out);
    }

    @Test
    public void testCalculateDaily() throws Exception {
        FareCalculator dailyFareCalculator = new DailyFareCalculator(new InMemoryDailyJourneyDao(),
                new InMemoryDailyCappingDaoImpl(),
                cappingService.getCapping(CappingType.DAILY));

        Journey journey = converter.convertToJourney("1,2022-02-21 10:20,2,1");
        int out = dailyFareCalculator.calculate(journey, 0);
        Assert.assertEquals(0, out);

        out = dailyFareCalculator.calculate(journey, 20);
        Assert.assertEquals(20, out);

        out = dailyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(30, out);

        out = dailyFareCalculator.calculate(journey, 50);
        Assert.assertEquals(50, out);

        out = dailyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(20, out);

        out = dailyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(0, out);

        out = dailyFareCalculator.calculate(journey, 30);
        Assert.assertEquals(0, out);
    }
}
