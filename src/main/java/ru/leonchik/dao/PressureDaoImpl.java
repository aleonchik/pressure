package ru.leonchik.dao;

import ru.leonchik.entiti.Pressure;
import ru.leonchik.exception.EntityNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PressureDaoImpl implements PressureDao {

    protected Connection conn = new DBConnect().getConnection();

    // Удалить все записи о давлении для пользователя userId
    @Override
    public void deleteAllForUerId(int userId) {
        String sql = "DELETE FROM pressure WHERE patient_id = ?";

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

    @Override
    public List<Pressure> all(int userId, int count) {
        String sql = "SELECT id, patient_id, sys, dia, pulse, dtm " +
                "FROM pressure " +
                "WHERE patient_id = ? AND (DATEDIFF(DAY, dtm, ?) < ?) ORDER BY dtm";

        /**
         * Берем последнюю дату записи для пользователя и от нее в обратную
         * сторону отсчитываем нужное количество последних дней
         */
        Date lastDate = getLastDate(userId);

        List<Pressure> press = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setDate(2, lastDate);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                press.add(new Pressure(
                        rs.getInt("id"),
                        userId,
                        rs.getInt("sys"),
                        rs.getInt("dia"),
                        rs.getInt("pulse"),
                        rs.getTimestamp("dtm").toLocalDateTime()));
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

    /**
     * Возвращаем последнюю дату записи для выбранного пользователя. Если записей нет - возвращаем текущую дату
     * @param userId
     * @return Date
     */
    Date getLastDate(int userId) {
        String sql = "SELECT dtm FROM pressure WHERE patient_id = ? ORDER BY dtm DESC LIMIT 1";
        LocalDateTime ld = LocalDateTime.now();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ld = rs.getTimestamp("dtm").toLocalDateTime();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Date.valueOf(ld.toLocalDate());
    }
}
