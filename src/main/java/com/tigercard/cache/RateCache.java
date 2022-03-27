package com.tigercard.cache;

import com.tigercard.loader.RateLoader;
import com.tigercard.models.RateData;
import com.tigercard.models.TimeRange;
import com.tigercard.models.Zone;

import java.util.List;
import java.util.Map;

public class RateCache {
    RateLoader rateLoader;
    RateData rateData;

    public RateCache(RateLoader rateLoader) {
        this.rateLoader = rateLoader;
        load();
    }

    public void load() {
        rateData = rateLoader.load();
    }

    public Map<Zone, Integer> getZoneToPeakMap() {
        return rateData.getZoneToPeakMap();
    }

    public Map<Zone, Integer> getZoneToOffPeakMap() {
        return rateData.getZoneToOffPeakMap();
    }

    public List<TimeRange> getPeakRanges() {
        return rateData.getPeakRanges();
    }

    public List<TimeRange> getPeakRangesWeekend() {
        return rateData.getPeakRangesWeekend();
    }
}
