package ru.kmikhails.metrolog.repository;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {

    public Connection getConnection() {
        File file = new File("metrolog.db");
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:file:" + file.getAbsolutePath());
        } catch (Exception e) {
//            LOG.error(e);
            throw new IllegalStateException("Ошибка подключения к базе данных", e);
        }
    }
}
