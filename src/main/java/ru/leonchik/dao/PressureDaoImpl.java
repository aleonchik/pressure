package ru.leonchik.dao;

import ru.leonchik.entiti.Patient;
import ru.leonchik.entiti.Pressure;
import ru.leonchik.exception.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PressureDaoImpl implements PressureDao {

    protected Connection conn = new DBConnect().getConnection();

    @Override
    public List<Pressure> all(int userId, int count) {
        String sql = "SELECT id, patient_id, sys, dia, pulse, dtm " +
                "FROM pressure WHERE patient_id = ? LIMIT ?";
        List<Pressure> press = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                press.add(new Pressure(
                        rs.getInt("id"),
                        userId,
                        rs.getInt("sys"),
                        rs.getInt("dia"),
                        rs.getInt("pulse"),
                        rs.getTimestamp("dtm").toLocalDateTime()));
//                        rs.getObject("dtm", LocalDateTime.class)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return press;
    }

    @Override
    public Pressure single(long pressureId) {
        return null;
    }

    @Override
    public void insert(Integer userId, Pressure pressure) {
        String sql = "INSERT INTO pressure (patient_id, sys, dia, pulse, dtm) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pressure.getPatientId());
            ps.setInt(2, pressure.getSys());
            ps.setInt(3, pressure.getDia());
            ps.setInt(4, pressure.getPulse());
//            ps.setObject(5, pressure.getDtm());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            LocalDateTime ldtm = pressure.getDtm();
//            ps.setString(5, ldtm.format(formatter));
            ps.setString(5, pressure.getDtm().format(formatter));
            ps.executeLargeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pressure pressure) throws EntityNotFoundException {

    }

    @Override
    public void delete(long pressureId) {
        String sql = "DELETE FROM pressure WHERE id = ?";

        try (PreparedStatement psDel = conn.prepareStatement(sql)) {
            try { psDel.setLong(1, pressureId); }
            catch (SQLException e) { throw new RuntimeException(e); }

            try {
                if (psDel.executeUpdate() > 0) {
                    System.out.println("Record with id=" + pressureId + " deleted");
                } else {
                    System.out.println("Record with id=" + pressureId + " NOT deleted\"");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
