package com.tigercard.dao;

import com.tigercard.models.Zone;

import java.time.LocalDate;
import java.util.Optional;

public interface JourneyDao1<T> {

    Optional<T> get(int id, LocalDate localDate);

    void save(T t);

    void saveCapping(int id, Zone zone, LocalDate localDate);

    int getCapping(int id, LocalDate localDate);

//    void update(T t, String[] params);
}
