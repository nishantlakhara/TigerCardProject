package com.tigercard.loader.impl;

import com.tigercard.loader.RateLoader;
import com.tigercard.models.RateData;
import com.tigercard.models.TimeRange;
import com.tigercard.models.Zone;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RateLoaderImpl implements RateLoader {
    private Map<Zone, Integer> zoneToPeakMap = new HashMap<>();
    private Map<Zone, Integer> zoneToOffPeakMap = new HashMap<>();
    private List<TimeRange> peakRanges = new ArrayList<>();
    private List<TimeRange> peakRangesWeekend = new ArrayList<>();

    private String zoneToPeakFile;
    private String zoneToOffPeakFile;
    private String peakRangeFile;
    private String peakRangeWeekendFile;

    public RateLoaderImpl(String zoneToPeakFile, String zoneToOffPeakFile, String peakRangeFile, String peakRangeWeekendFile) {
        this.zoneToPeakFile = zoneToPeakFile;
        this.zoneToOffPeakFile = zoneToOffPeakFile;
        this.peakRangeFile = peakRangeFile;
        this.peakRangeWeekendFile = peakRangeWeekendFile;
    }

    @Override
    public RateData load() throws Exception {
        loadPeakMap(zoneToPeakMap, zoneToPeakFile);

        loadPeakMap(zoneToOffPeakMap, zoneToOffPeakFile);

        loadPeakRanges(peakRanges, peakRangeFile);

        loadPeakRanges(peakRangesWeekend, peakRangeWeekendFile);

        return new RateData(zoneToPeakMap, zoneToOffPeakMap, peakRanges, peakRangesWeekend);
    }

    private void loadPeakRanges(List<TimeRange> peakRanges, String peakRangeFile) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource(peakRangeFile).toURI());
            try (Stream<String> stream = Files.lines(path)) {
                stream
                        .skip(1)
                        .forEach(line -> updatePeakRanges(peakRanges, line));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void updatePeakRanges(List<TimeRange> peakRanges, String line) {
        String[] split = line.split(",");

        String[] from = split[0].split(":");
        String[] to = split[1].split(":");

        TimeRange timeRange = new TimeRange(
                LocalTime.of(Integer.parseInt(from[0]), Integer.parseInt(from[1])),
                LocalTime.of(Integer.parseInt(to[0]), Integer.parseInt(to[1]))
        );
        peakRanges.add(timeRange);
    }

    private void loadPeakMap(Map<Zone, Integer> zoneToPeakMap, String peakFileName) {
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource(peakFileName).toURI());
            try (Stream<String> stream = Files.lines(path)) {
                stream
                        .skip(1)
                        .forEach(line -> updatePeakHourData(zoneToPeakMap, line));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void updatePeakHourData(Map<Zone, Integer> zoneToPeakMap, String line) {
        String[] split = line.split(",");

        int from = Integer.parseInt(split[0]);
        int to = Integer.parseInt(split[1]);
        int rate = Integer.parseInt(split[2]);

        zoneToPeakMap.putIfAbsent(new Zone(from, to), rate);
    }
}
