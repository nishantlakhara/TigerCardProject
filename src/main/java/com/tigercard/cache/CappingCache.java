package com.tigercard.cache;

import com.tigercard.enums.CappingType;
import com.tigercard.loader.FareCappingLoader;
import com.tigercard.models.FareCappingData;
import com.tigercard.models.Zone;

import java.util.Map;

public class CappingCache {
    FareCappingLoader fareCappingLoader;
    FareCappingData fareCappingData;

    public CappingCache(FareCappingLoader fareCappingLoader) {
        this.fareCappingLoader = fareCappingLoader;
    }

    public void loadCapping() throws Exception {
        fareCappingData = fareCappingLoader.load();
    }

    public Map<Zone, Integer> getFareCappingData(CappingType cappingType) {
        return fareCappingData.getFareCapping(cappingType);
    }
}
