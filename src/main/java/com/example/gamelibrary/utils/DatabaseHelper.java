package com.example.gamelibrary.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa pomocnicza do zarządzania połączeniem z bazą danych.
 * Zapewnia metodę do uzyskania połączenia z bazą danych za pomocą JDBC.
 */
public class DatabaseHelper {

    /**
     * URL do połączenia z bazą
     */
    private static final String URL = "";
    /**
     * Nazwa użytkownika do połączenia z bazą
     */
    private static final String USER = "";
    /**
     * Hasło do połączenia z bazą
     */
    private static final String PASSWORD = "";

    /**
     * Uzyskuje połączenie z bazą danych.
     *
     * @return Połączenie z bazą danych
     * @throws SQLException Jeżeli wystąpi błąd podczas uzyskiwania połączenia
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
