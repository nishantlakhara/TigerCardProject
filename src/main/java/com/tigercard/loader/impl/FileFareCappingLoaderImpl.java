package com.tigercard.loader.impl;

import com.tigercard.enums.CappingType;
import com.tigercard.loader.FareCappingLoader;
import com.tigercard.models.FareCappingData;
import com.tigercard.models.Zone;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileFareCappingLoaderImpl implements FareCappingLoader {
    private String fileName;

    public FileFareCappingLoaderImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public FareCappingData load() {
        final Map<CappingType, Map<Zone, Integer>> fareCapping = new HashMap<>();
        Path path = null;
        try {
            path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
            try (Stream<String> stream = Files.lines(path)) {
                stream
                        .skip(1)
                        .forEach(line -> updateCappingData(fareCapping, line));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new FareCappingData(fareCapping);
    }

    private void updateCappingData(Map<CappingType, Map<Zone, Integer>> fareCapping, String line) {
        String[] split = line.split(",");
        CappingType cappingType = CappingType.valueOf(split[0]);
        int from = Integer.valueOf(split[1]);
        int to = Integer.valueOf(split[2]);
        int capping = Integer.valueOf(split[3]);

        fareCapping.putIfAbsent(cappingType, new HashMap<>());
        fareCapping.get(cappingType).putIfAbsent(new Zone(from, to), capping);
    }
}
