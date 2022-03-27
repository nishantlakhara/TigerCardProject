package com.tigercard.service;

import com.tigercard.cache.CappingCache;
import com.tigercard.enums.CappingType;
import com.tigercard.models.Zone;

import java.util.Map;

public class CappingService {
    private CappingCache cappingCache;

    public CappingService(CappingCache cappingCache) {
        this.cappingCache = cappingCache;
    }

    public Map<Zone, Integer> getCapping(CappingType cappingType) {
        return cappingCache.getFareCappingData(cappingType);
    }
}
