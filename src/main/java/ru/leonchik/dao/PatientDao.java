package ru.leonchik.dao;

import ru.leonchik.entiti.Patient;
import ru.leonchik.exception.EntityNotFoundException;

import java.util.List;

public interface PatientDao {
    List<Patient> all();
    Patient single(int id);

    void insert(Patient patient);
    void update(Patient patient) throws EntityNotFoundException;
//    void delete(Long patientId);
    void delete(Patient patient);
    void deletePatient(int userId);
}
