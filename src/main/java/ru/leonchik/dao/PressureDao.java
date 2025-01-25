package ru.leonchik.dao;

import ru.leonchik.entiti.Pressure;
import ru.leonchik.exception.EntityNotFoundException;

import java.util.List;

public interface PressureDao {
    List<Pressure> all(int userId, int count);
    Pressure single(long pressureId);
    void insert(Integer userId, Pressure pressure);
    void update(Pressure pressure)  throws EntityNotFoundException;
    void delete(long pressureId);
    void deleteAllForUerId(int userId);
}
