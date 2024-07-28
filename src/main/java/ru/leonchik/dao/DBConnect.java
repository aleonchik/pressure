package ru.leonchik.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    private String DB_DRIVER_KEY = "db.driver";
    private String DB_URL_KEY = "db.url";
    private String DB_USER_NAME_KEY = "db.user";
    private String DB_PASS_KEY = "db.pass";

    public Connection getConnection() {
        try {
            Class.forName(PropertiesUtil.get(DB_DRIVER_KEY));
            Connection conn = DriverManager.getConnection(
                    PropertiesUtil.get(DB_URL_KEY),
                    PropertiesUtil.get(DB_USER_NAME_KEY),
                    PropertiesUtil.get(DB_PASS_KEY));
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
