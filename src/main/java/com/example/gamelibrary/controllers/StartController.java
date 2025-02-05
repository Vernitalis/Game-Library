package com.example.gamelibrary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler odpowiedzialny za obsługę widoku startowego aplikacji.
 * Umożliwia użytkownikowi przejście do okien logowania lub rejestracji,
 * a także zamknięcie aplikacji.
 */
public class StartController {

    /**
     * Obecna scena aplikacji.
     */
    private Stage stage;

    /**
     * Obecna scena wyświetlana w aplikacji.
     */
    private Scene scene;

    /**
     * Główny kontener dla elementów w oknie startowym.
     */
    @FXML
    private AnchorPane anchorPane;

    /**
     * Przycisk zamykający aplikację.
     */
    @FXML
    private Button btnExit;

    /**
     * Przycisk przechodzący do okna logowania.
     */
    @FXML
    private Button btnLogin;

    /**
     * Przycisk przechodzący do okna rejestracji.
     */
    @FXML
    private Button btnRegister;

    /**
     * Obsługuje proces zamykania aplikacji.
     * Wyświetla okno dialogowe z potwierdzeniem przed zamknięciem programu.
     *
     * @param event zdarzenie wywołane przez użytkownika, np. kliknięcie przycisku zamykania.
     */
    public void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Zamykanie");
        alert.setHeaderText("Opuszczasz program");
        alert.setContentText("Czy na pewno chcesz opuścić program?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/exit-icon.png")));

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Przekierowuje użytkownika do widoku logowania.
     *
     * @param actionEvent zdarzenie wywołane przez użytkownika, np. kliknięcie przycisku logowania.
     * @throws IOException w przypadku problemu z załadowaniem widoku logowania.
     */
    public void switchToLogin(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Przekierowuje użytkownika do widoku rejestracji.
     *
     * @param actionEvent zdarzenie wywołane przez użytkownika, np. kliknięcie przycisku rejestracji.
     * @throws IOException w przypadku problemu z załadowaniem widoku rejestracji.
     */
    public void switchToRegister(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/register-view.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
