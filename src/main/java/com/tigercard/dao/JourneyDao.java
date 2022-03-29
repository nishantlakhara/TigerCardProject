package com.tigercard.dao;

import java.time.LocalDate;
import java.util.Optional;

public interface JourneyDao<T> {

    Optional<T> get(int id, LocalDate localDate) throws Exception;

    void save(T t) throws Exception;

//    void update(T t, String[] params);
}
