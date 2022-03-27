package com.tigercard.dao;

import java.time.LocalDate;
import java.util.Optional;

public interface CappingDao<T> {
    void saveCapping(T capping);

    Optional<T> getCapping(int id, LocalDate localDate);
}
