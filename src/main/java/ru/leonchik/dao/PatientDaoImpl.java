package ru.leonchik.dao;

import ru.leonchik.entiti.Patient;
import ru.leonchik.exception.EntityNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {

    protected Connection conn = new DBConnect().getConnection();

    @Override
    public List<Patient> all() {
        String sql = "SELECT id, name, birth FROM patient ORDER BY name";
        List<Patient> allPat = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//                LocalDate ld = rs.getDate(3).toLocalDate();
                allPat.add(new Patient(rs.getInt(1), rs.getString(2), rs.getDate(3).toLocalDate()));
            }

            if (!rs.isClosed()) rs.close();
//            if (!ps.isClosed()) ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allPat;
    }

    @Override
    public Patient single(int id) {
        String sql = "SELECT id, name, birth FROM patient WHERE id = ?";

        Patient patient = new Patient();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setBirth(rs.getDate("birth").toLocalDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patient;
//        return data().stream().filter(e -> e.getId() == id).findFirst().get();
    }

    @Override
    public void insert(Patient patient) {
        String sql = "INSERT INTO patient (name, birth) VALUES (?, ?)";
        int result = 0;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getName());
            ps.setDate(2, java.sql.Date.valueOf(patient.getBirth()));
            result = ps.executeUpdate();
//            if (!ps.isClosed()) ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Обновляем
    @Override
    public void update(Patient patient) throws EntityNotFoundException {
        // UPDATE patient SET name='Лена2', birth='1980-1-1' WHERE id = 8
        String sql = "UPDATE patient SET name = ?, birth = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getName());
            ps.setDate(2, java.sql.Date.valueOf(patient.getBirth()));
            ps.setInt(3, patient.getId());
            boolean result = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Patient patient) {
//        data().remove(patient);
    }

    // Удаляем пользователя
    @Override
    public void deletePatient(int userId) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (PreparedStatement psDel = conn.prepareStatement(sql)) {
            try { psDel.setLong(1, userId); }
            catch (SQLException e) { throw new RuntimeException(e); }

            try {
                if (psDel.executeUpdate() > 0) {
                    System.out.println("Record with id=" + userId + " deleted");
                } else {
                    System.out.println("Record with id=" + userId + " NOT deleted\"");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Patient> data() {
        List<Patient> data = new ArrayList<>();
        data.add(new Patient(1, "Alexey", LocalDate.of(1971, 4, 16)));
        data.add(new Patient(2, "Olga", LocalDate.of(1960, 5, 17)));
        data.add(new Patient(3, "Serega", LocalDate.of(1976, 9, 13)));
        data.add(new Patient(4, "Seva", LocalDate.of(2015, 2, 15)));

        return data;
    }

    /*private long nextId() {
        return data().get(data().size() - 1).getId();
    }*/
}
