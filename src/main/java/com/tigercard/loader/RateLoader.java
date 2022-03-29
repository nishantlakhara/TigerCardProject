package com.tigercard.loader;

import com.tigercard.models.RateData;

public interface RateLoader {
    public RateData load() throws Exception;
}
