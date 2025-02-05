package com.example.gamelibrary.services;

import com.example.gamelibrary.models.User;
import com.example.gamelibrary.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Klasa odpowiedzialna za operacje związane z użytkownikami w systemie, takie jak logowanie, rejestracja i pobieranie danych o użytkownikach.
 */
public class UserService {

    /**
     * Pobiera dane użytkownika na podstawie jego identyfikatora.
     *
     * @param userId ID użytkownika (login).
     * @return Zwraca obiekt typu User zawierający dane użytkownika, lub null, jeśli użytkownik o podanym ID nie istnieje.
     */
    public User getUser(String userId) {
        String query = "SELECT * FROM projekt.uzytkownicy WHERE login = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String user_id = resultSet.getString("id_uzytkownika");
                    String login = resultSet.getString("login");
                    String haslo = resultSet.getString("haslo");
                    String email = resultSet.getString("email");
                    String role = resultSet.getString("rola");
                    return new User(user_id, login, haslo, email, role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sprawdza, czy podany login i hasło są poprawne.
     *
     * @param userId   ID użytkownika (login).
     * @param password Hasło użytkownika.
     * @return Zwraca true, jeśli dane logowania są poprawne, w przeciwnym razie false.
     */
    public boolean checkLogin(String userId, String password) {
        String query = "SELECT projekt.SprawdzDaneLogowania(?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Dodaje nowego użytkownika do systemu.
     *
     * @param user Obiekt typu User zawierający dane nowego użytkownika.
     * @return Zwraca true, jeśli użytkownik został pomyślnie dodany, w przeciwnym razie false.
     */
    public boolean addUser(User user) {
        String insert = "INSERT INTO projekt.uzytkownicy (login, haslo, email) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(insert)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            try {
                int rows = statement.executeUpdate();
                if (rows == 1) {
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("Błąd zapytania " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
