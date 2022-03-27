package com.tigercard.models;

import com.tigercard.enums.CappingType;
import java.util.Collections;
import java.util.Map;

public class FareCappingData {
    private final Map<CappingType, Map<Zone, Integer>> fareCapping;

    public FareCappingData(Map<CappingType, Map<Zone, Integer>> fareCapping) {
        this.fareCapping = fareCapping;
    }

    public Map<Zone, Integer> getFareCapping(CappingType cappingType) {
        return Collections.unmodifiableMap(fareCapping.get(cappingType));
    }
}
