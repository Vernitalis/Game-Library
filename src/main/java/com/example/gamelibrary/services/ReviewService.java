package com.example.gamelibrary.services;

import com.example.gamelibrary.models.Game;
import com.example.gamelibrary.models.Review;
import com.example.gamelibrary.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa odpowiedzialna za obsługę operacji związanych z recenzjami gier w bazie danych.
 * Zawiera metody do dodawania, usuwania, aktualizowania i pobierania recenzji.
 */

public class ReviewService {

    /**
     * Sprawdza, czy recenzja należy do użytkownika o podanym identyfikatorze.
     *
     * @param userID   ID użytkownika.
     * @param reviewID ID recenzji.
     * @return Zwraca true, jeśli recenzja należy do użytkownika, w przeciwnym razie false.
     */
    public boolean reviewBelongsToUserID(String userID, String reviewID) {
        String query = "SELECT projekt.CzyRecenzjaJestUzytkownika(?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(userID));
            preparedStatement.setInt(2, Integer.parseInt(reviewID));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sprawdza, czy użytkownik dodał już recenzję do wybranej gry.
     *
     * @param selectedGame Gra, której recenzja jest sprawdzana.
     * @param userID       ID użytkownika.
     * @return Zwraca true, jeśli recenzja już istnieje, w przeciwnym razie false.
     */
    public boolean reviewAlreadyExists(Game selectedGame, String userID) {
        String queryReviewExists = "SELECT projekt.CzyRecenzjaIstnieje(?, ?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryReviewExists)) {
            preparedStatement.setInt(1, Integer.parseInt(userID));
            preparedStatement.setInt(2, Integer.parseInt(selectedGame.getGame_id()));
            ResultSet rs = preparedStatement.executeQuery();
            boolean exists = false;
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Dodaje nową recenzję do bazy danych.
     *
     * @param selectedGame Gra, której dotyczy recenzja.
     * @param userID       ID użytkownika, który dodaje recenzję.
     * @param rating       Ocena recenzji (w skali liczbowej).
     * @param comment      Komentarz użytkownika.
     */
    public void addReview(Game selectedGame, String userID, String rating, String comment) {
        String queryAddReview = "INSERT INTO projekt.Recenzje (id_gry, id_uzytkownika, ocena, komentarz) VALUES(?,?,?,?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryAddReview)) {
            preparedStatement.setInt(1, Integer.parseInt(selectedGame.getGame_id()));
            preparedStatement.setInt(2, Integer.parseInt(userID));
            preparedStatement.setFloat(3, Float.parseFloat(rating));
            preparedStatement.setString(4, comment);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Usuwa wybraną recenzję z bazy danych.
     *
     * @param selectedReview Recenzja, która ma zostać usunięta.
     * @param userId         ID użytkownika usuwającego recenzję.
     */
    public void deleteReview(Review selectedReview, String userId) {
        String query = "DELETE FROM projekt.Recenzje WHERE id_recenzji = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(selectedReview.getReviewID()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aktualizuje istniejącą recenzję.
     *
     * @param selectedReview Recenzja, która ma zostać zaktualizowana.
     * @param rating         Nowa ocena recenzji.
     * @param comment        Nowy komentarz recenzji.
     */
    public void updateReview(Review selectedReview, String rating, String comment) {
        String queryUpdateReview = "UPDATE projekt.Recenzje SET ocena = ?, komentarz = ?, data_recenzji = CURRENT_DATE WHERE id_recenzji = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateReview)) {

            preparedStatement.setFloat(1, Float.parseFloat(rating));
            preparedStatement.setString(2, comment);
            preparedStatement.setInt(3, Integer.parseInt(selectedReview.getReviewID()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Pobiera wszystkie recenzje z bazy danych.
     *
     * @return Lista wszystkich recenzji.
     */
    public List<Review> getAllReviews() {
        String query = "SELECT * FROM projekt.WidokRecenzje";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String reviewID = rs.getString("id_recenzji");
                String author = rs.getString("autor");
                String gameName = rs.getString("nazwa_gry");
                String rating = rs.getString("ocena");
                String comment = rs.getString("komentarz");
                String date = rs.getString("data_recenzji");

                reviews.add(new Review(reviewID, author, gameName, rating, comment, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

}
