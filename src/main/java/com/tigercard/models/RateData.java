package com.tigercard.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RateData {
    private final Map<Zone, Integer> zoneToPeakMap;
    private final Map<Zone, Integer> zoneToOffPeakMap;
    private final List<TimeRange> peakRanges;
    private final List<TimeRange> peakRangesWeekend;

    public RateData(Map<Zone, Integer> zoneToPeakMap, Map<Zone, Integer> zoneToOffPeakMap, List<TimeRange> peakRanges, List<TimeRange> peakRangesWeekend) {
        this.zoneToPeakMap = zoneToPeakMap;
        this.zoneToOffPeakMap = zoneToOffPeakMap;
        this.peakRanges = peakRanges;
        this.peakRangesWeekend = peakRangesWeekend;
    }

    public Map<Zone, Integer> getZoneToPeakMap() {
        return Collections.unmodifiableMap(zoneToPeakMap);
    }

    public Map<Zone, Integer> getZoneToOffPeakMap() {
        return Collections.unmodifiableMap(zoneToOffPeakMap);
    }

    public List<TimeRange> getPeakRanges() {
        return Collections.unmodifiableList(peakRanges);
    }

    public List<TimeRange> getPeakRangesWeekend() {
        return Collections.unmodifiableList(peakRangesWeekend);
    }
}
