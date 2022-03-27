package com.tigercard.dao;

import com.tigercard.models.Zone;

import java.time.LocalDate;
import java.util.Optional;

public interface JourneyDao<T> {

    Optional<T> get(int id, LocalDate localDate);

    void save(T t);

//    void update(T t, String[] params);
}
