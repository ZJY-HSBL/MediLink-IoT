package io.medilink.util;

import io.medilink.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Db {
    private Db() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                AppConfig.required("db.url"),
                AppConfig.required("db.username"),
                AppConfig.required("db.password")
        );
    }
}
