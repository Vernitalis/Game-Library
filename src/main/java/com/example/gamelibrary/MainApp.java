package com.example.gamelibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Główna klasa aplikacji, która uruchamia bibliotekę gier.
 * Odpowiada za inicjalizację interfejsu użytkownika oraz ustawienie sceny i okna aplikacji.
 */
public class MainApp extends Application {

    /**
     * Metoda start() odpowiedzialna za uruchomienie aplikacji i wyświetlenie głównego okna.
     * Ładuje plik FXML, ustawia styl CSS oraz ikonę aplikacji.
     * Ustawia także akcję zamykania okna aplikacji z potwierdzeniem.
     *
     * @param primaryStage Główne okno aplikacji
     * @throws Exception W przypadku błędu podczas ładowania zasobów lub sceny
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start-view.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/book-icon.png")));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Biblioteka Gier");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });
    }

    /**
     * Metoda odpowiedzialna za wyświetlenie okna potwierdzenia przed zamknięciem aplikacji.
     * Jeśli użytkownik potwierdzi, aplikacja zostanie zamknięta.
     *
     * @param primaryStage Główne okno aplikacji
     */
    public void exit(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Zamykanie");
        alert.setHeaderText("Opuszczasz program");
        alert.setContentText("Czy na pewno chcesz opuścić program?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/exit-icon.png")));

        if (alert.showAndWait().get() == ButtonType.OK) {
            primaryStage.close();
        }
    }

    /**
     * Metoda główna, która uruchamia aplikację.
     *
     * @param args Argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        launch(args);
    }
}
