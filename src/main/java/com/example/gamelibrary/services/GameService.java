package com.example.gamelibrary.services;

import com.example.gamelibrary.models.Game;
import com.example.gamelibrary.models.User;
import com.example.gamelibrary.utils.DatabaseHelper;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa serwisu zarządzającego operacjami na danych dotyczących gier w bibliotece użytkownika.
 * Obsługuje dodawanie, usuwanie, aktualizowanie i pobieranie gier z bazy danych.
 */
public class GameService {

    /**
     * Dodaje nowy rekord do bazy danych, jeśli dany rekord jeszcze nie istnieje.
     *
     * @param connection  połączenie z bazą danych.
     * @param queryCheck  zapytanie SQL sprawdzające istnienie rekordu.
     * @param queryInsert zapytanie SQL dodające nowy rekord.
     * @param param       parametr do zapytania SQL.
     * @throws SQLException jeśli wystąpi błąd SQL.
     */
    public void insertIfNotExists(Connection connection, String queryCheck, String queryInsert, String param) throws SQLException {
        PreparedStatement appStatement = connection.prepareStatement(queryCheck);
        appStatement.setString(1, param);
        ResultSet appRS = appStatement.executeQuery();
        if (appRS.next()) {
            boolean appExists = appRS.getBoolean(1);
            if (!appExists) {
                try (PreparedStatement innerAppStatement = connection.prepareStatement(queryInsert)) {
                    innerAppStatement.setString(1, param);
                    innerAppStatement.executeUpdate();
                }
            }
        }
    }

    /**
     * Pobiera identyfikator rekordu na podstawie jego nazwy.
     *
     * @param connection połączenie z bazą danych.
     * @param queryID    zapytanie SQL pobierające identyfikator.
     * @param fieldName  nazwa pola, które zawiera identyfikator.
     * @param name       nazwa rekordu do wyszukania.
     * @return identyfikator rekordu jako String.
     * @throws SQLException jeśli wystąpi błąd SQL.
     */
    public String getIDByName(Connection connection, String queryID, String fieldName, String name) throws SQLException {
        String returnID = "";
        PreparedStatement innerStatement = connection.prepareStatement(queryID);
        innerStatement.setString(1, name);
        ResultSet innerRS = innerStatement.executeQuery();
        if (innerRS.next()) {
            returnID = innerRS.getString(fieldName);
        }
        return returnID;
    }

    /**
     * Dodaje nową grę do biblioteki użytkownika, uwzględniając jej powiązania z twórcami i aplikacjami.
     *
     * @param newGame      obiekt reprezentujący nową grę.
     * @param developer    nazwa studia twórcy gry.
     * @param price        cena zakupu gry.
     * @param application  nazwa aplikacji powiązanej z grą.
     * @param purchaseDate data zakupu gry.
     * @param userID       identyfikator użytkownika.
     */
    public void addGame(Game newGame, String developer, String price, String application, String purchaseDate, String userID) {
        Game insertedGame = new Game("Failed", "To Insert");
        String gameID = "";
        String appID = "";
        String devID = "";
        String queryGameExistsCheck = "SELECT projekt.CzyGraIstnieje(?)";
        String queryAppExistsCheck = "SELECT projekt.CzyAplikacjaIstnieje(?)";
        String queryDevExistsCheck = "SELECT projekt.CzyTworcaIstnieje(?)";
        String queryGameAppExistsCheck = "SELECT projekt.CzyGraAplikacjaIstnieje(?, ?)";
        String queryGameDevExistsCheck = "SELECT projekt.CzyGraTworcaIstnieje(?, ?)";
        String queryGameAlreadyPurchased = "SELECT projekt.CzyGraJestZakupiona(?, ?)";
        String queryInsertGame = "INSERT INTO projekt.Gry (nazwa, platforma) VALUES (?, ?)";
        String queryInsertApp = "INSERT INTO projekt.Aplikacje (nazwa_aplikacji) VALUES (?)";
        String queryInsertDev = "INSERT INTO projekt.Tworcy (nazwa_studia) VALUES (?)";
        String queryGameID = "SELECT id_gry FROM projekt.Gry WHERE nazwa = ?";
        String queryAppID = "SELECT id_aplikacji FROM projekt.Aplikacje WHERE nazwa_aplikacji = ?";
        String queryDevID = "SELECT id_tworcy FROM projekt.Tworcy WHERE nazwa_studia = ?";
        String queryInsertGameApp = "INSERT INTO projekt.Gry_Aplikacje (id_gry, id_aplikacji) VALUES (?, ?)";
        String queryInsertGameDev = "INSERT INTO projekt.Gry_Tworcy (id_gry, id_tworcy) VALUES (?, ?)";
        String queryInsertPurchasedGame = """
                  INSERT INTO projekt.ZakupioneGry (id_gry, id_uzytkownika, id_aplikacji, data_zakupu, cena_zakupu)
                  VALUES (?, ?, ?, ?, ?)
                """;
        //dodanie do tabeli gry
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryGameExistsCheck)) {
            statement.setString(1, newGame.getName());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                boolean gameExists = rs.getBoolean(1);
                if (!gameExists) {
                    try (PreparedStatement innerStatement = connection.prepareStatement(queryInsertGame)) {
                        innerStatement.setString(1, newGame.getName());
                        innerStatement.setString(2, newGame.getPlatform());

                        innerStatement.executeUpdate();
                    }
                }
                try (PreparedStatement innerStatement = connection.prepareStatement(queryGameID)) {
                    innerStatement.setString(1, newGame.getName());
                    ResultSet innerRS = innerStatement.executeQuery();
                    if (innerRS.next()) {
                        gameID = innerRS.getString("id_gry");
                        insertedGame = new Game(gameID, newGame.getName(), newGame.getPlatform());
                    }
                }
            }
            //dodanie do tabeli gry aplikacje
            try (PreparedStatement innerStatement = connection.prepareStatement(queryAppExistsCheck)) {
                innerStatement.setString(1, application);
                ResultSet innerRS = innerStatement.executeQuery();
                if (innerRS.next()) {
                    boolean appExists = innerRS.getBoolean(1);
                    if (!appExists) {
                        try (PreparedStatement innerAppStatement = connection.prepareStatement(queryInsertApp)) {
                            innerAppStatement.setString(1, application);

                            innerAppStatement.executeUpdate();
                        }
                    }
                    try (PreparedStatement innerAppStatement = connection.prepareStatement(queryAppID)) {
                        innerAppStatement.setString(1, application);
                        ResultSet innerAppRS = innerAppStatement.executeQuery();
                        if (innerAppRS.next()) {
                            appID = innerAppRS.getString("id_aplikacji");
                        }
                    }
                    try (PreparedStatement innerAppStatement = connection.prepareStatement(queryGameAppExistsCheck)) {
                        innerAppStatement.setInt(1, Integer.parseInt(gameID));
                        innerAppStatement.setInt(2, Integer.parseInt(appID));
                        ResultSet innerAppRS = innerAppStatement.executeQuery();
                        if (innerAppRS.next()) {
                            boolean gameAppExists = innerAppRS.getBoolean(1);
                            if (!gameAppExists) {
                                try (PreparedStatement innerGameAppStatement = connection.prepareStatement(queryInsertGameApp)) {
                                    innerGameAppStatement.setInt(1, Integer.parseInt(gameID));
                                    innerGameAppStatement.setInt(2, Integer.parseInt(appID));

                                    innerGameAppStatement.executeUpdate();
                                }
                            }
                        }
                    }

                }
            }
            //dodanie do tabeli gry tworcy
            try (PreparedStatement innerStatement = connection.prepareStatement(queryDevExistsCheck)) {
                innerStatement.setString(1, developer);
                ResultSet innerRS = innerStatement.executeQuery();
                if (innerRS.next()) {
                    boolean devExists = innerRS.getBoolean(1);
                    if (!devExists) {
                        try (PreparedStatement innerDevStatement = connection.prepareStatement(queryInsertDev)) {
                            innerDevStatement.setString(1, developer);

                            innerDevStatement.executeUpdate();
                        }
                    }
                    try (PreparedStatement innerDevStatement = connection.prepareStatement(queryDevID)) {
                        innerDevStatement.setString(1, developer);
                        ResultSet innerAppRS = innerDevStatement.executeQuery();
                        if (innerAppRS.next()) {
                            devID = innerAppRS.getString("id_tworcy");
                        }
                    }
                    try (PreparedStatement innerDevStatement = connection.prepareStatement(queryGameDevExistsCheck)) {
                        innerDevStatement.setInt(1, Integer.parseInt(gameID));
                        innerDevStatement.setInt(2, Integer.parseInt(devID));
                        ResultSet innerDevRS = innerDevStatement.executeQuery();
                        if (innerDevRS.next()) {
                            boolean gameDevExists = innerDevRS.getBoolean(1);
                            if (!gameDevExists) {
                                try (PreparedStatement innerGameDevStatement = connection.prepareStatement(queryInsertGameDev)) {
                                    innerGameDevStatement.setInt(1, Integer.parseInt(gameID));
                                    innerGameDevStatement.setInt(2, Integer.parseInt(devID));

                                    innerGameDevStatement.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }

            //dodanie do tabeli zakupionegry
            try (PreparedStatement innerStatement = connection.prepareStatement(queryGameAlreadyPurchased)) {
                innerStatement.setInt(1, Integer.parseInt(gameID));
                innerStatement.setInt(2, Integer.parseInt(userID));

                ResultSet innerRS = innerStatement.executeQuery();
                if (innerRS.next()) {
                    boolean gameAlreadyPurchased = innerRS.getBoolean(1);
                    if (!gameAlreadyPurchased) {
                        try (PreparedStatement innerPurchaseStatement = connection.prepareStatement(queryInsertPurchasedGame)) {

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            LocalDate purchaseDateLocal = LocalDate.parse(purchaseDate, formatter);

                            innerPurchaseStatement.setInt(1, Integer.parseInt(insertedGame.getGame_id()));
                            innerPurchaseStatement.setInt(2, Integer.parseInt(userID));
                            innerPurchaseStatement.setInt(3, Integer.parseInt(appID));
                            innerPurchaseStatement.setDate(4, Date.valueOf(purchaseDateLocal));
                            innerPurchaseStatement.setFloat(5, Float.parseFloat(price));

                            innerPurchaseStatement.executeUpdate();

                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuwa wybraną grę z biblioteki użytkownika.
     *
     * @param selectedGame obiekt reprezentujący grę do usunięcia.
     * @param userID       identyfikator użytkownika.
     */
    public void deleteGame(Game selectedGame, String userID) {
        String query = "DELETE FROM projekt.ZakupioneGry WHERE id_gry = ? AND id_uzytkownika = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(query)) {
            deleteStatement.setInt(1, Integer.parseInt(selectedGame.getGame_id()));
            deleteStatement.setInt(2, Integer.parseInt(userID));

            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aktualizuje dane wybranej gry w bazie danych.
     *
     * @param selectedGame obiekt reprezentujący grę do aktualizacji.
     * @param newName      nowa nazwa gry.
     * @param newPlatform  nowa platforma gry.
     * @param newDev       nowe studio twórcy gry.
     * @param newApp       nowa aplikacja powiązana z grą.
     */
    public void updateGame(Game selectedGame, String newName, String newPlatform, String newDev, String newApp) {
        int gameID = Integer.parseInt(selectedGame.getGame_id());
        String appID = "";
        String developerID = "";

        String queryAppExistsCheck = "SELECT projekt.CzyAplikacjaIstnieje(?)";
        String queryDevExistsCheck = "SELECT projekt.CzyTworcaIstnieje(?)";
        String queryAppID = "SELECT id_aplikacji FROM projekt.Aplikacje WHERE nazwa_aplikacji = ?";
        String queryDevID = "SELECT id_tworcy FROM projekt.Tworcy WHERE nazwa_studia = ?";
        String queryUpdateGame = "UPDATE projekt.Gry SET nazwa = ?, platforma = ? WHERE id_gry = ?";
        String queryUpdateGameDev = "UPDATE projekt.Gry_Tworcy SET id_tworcy = ? WHERE id_gry = ?";
        String queryUpdateGameApp = "UPDATE projekt.Gry_Aplikacje SET id_aplikacji = ? WHERE id_gry = ?";
        String queryInsertApp = "INSERT INTO projekt.Aplikacje (nazwa_aplikacji) VALUES (?)";
        String queryInsertDev = "INSERT INTO projekt.Tworcy (nazwa_studia) VALUES (?)";

        try (Connection connection = DatabaseHelper.getConnection()) {
            insertIfNotExists(connection, queryAppExistsCheck, queryInsertApp, newApp);
            insertIfNotExists(connection, queryDevExistsCheck, queryInsertDev, newDev);
            appID = getIDByName(connection, queryAppID, "id_aplikacji", newApp);
            developerID = getIDByName(connection, queryDevID, "id_tworcy", newDev);

            try (PreparedStatement updateGameStatement = connection.prepareStatement(queryUpdateGame)) {
                updateGameStatement.setString(1, newName);
                updateGameStatement.setString(2, newPlatform);
                updateGameStatement.setInt(3, gameID);
                updateGameStatement.executeUpdate();

                try (PreparedStatement updateDevStatement = connection.prepareStatement(queryUpdateGameDev)) {
                    updateDevStatement.setInt(1, Integer.parseInt(developerID));
                    updateDevStatement.setInt(2, gameID);
                    updateDevStatement.executeUpdate();
                }
                try (PreparedStatement updateAppStatement = connection.prepareStatement(queryUpdateGameApp)) {
                    updateAppStatement.setInt(1, Integer.parseInt(appID));
                    updateAppStatement.setInt(2, gameID);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pobiera listę wszystkich gier dla danego użytkownika.
     *
     * @param user obiekt reprezentujący użytkownika, którego gry mają zostać pobrane.
     * @return lista gier powiązanych z użytkownikiem.
     */
    public List<Game> getAllGamesForUser(User user) {
        List<Game> games = new ArrayList<>();
        String query = "";
        if(user.getRole().equals("admin")) {
            query = """
                SELECT
                    gta.id_gry,
                    gta.nazwa,
                    gta.platforma,
                    gta.cena_zakupu,
                    gta.data_zakupu,
                    gta.dev,
                    gta.app
                FROM projekt.WidokGryTworcyAplikacje gta
                """;
        } else {
            query = """
                SELECT
                    gta.id_gry,
                    gta.nazwa,
                    gta.platforma,
                    gta.cena_zakupu,
                    gta.data_zakupu,
                    gta.dev,
                    gta.app
                FROM projekt.WidokGryTworcyAplikacje gta
                WHERE gta.id_uzytkownika = ?
                ORDER BY gta.id_gry;
                """;
        }
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (!user.getRole().equals("admin")) {
                statement.setInt(1, Integer.parseInt(user.getUser_id()));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String gameId = resultSet.getString("id_gry");
                    String name = resultSet.getString("nazwa");
                    String platform = resultSet.getString("platforma");
                    String price = resultSet.getString("cena_zakupu");
                    String purchaseDate = resultSet.getString("data_zakupu");
                    String developer = resultSet.getString("dev");
                    String application = resultSet.getString("app");

                    games.add(new Game(gameId, name, platform, price, purchaseDate, developer, application));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }


}

