package com.example.gamelibrary.controllers;

import com.example.gamelibrary.models.User;
import com.example.gamelibrary.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler odpowiedzialny za obsługę logowania użytkowników do aplikacji.
 */
public class LoginController {

    /**
     * Pole tekstowe do wprowadzenia nazwy użytkownika.
     */
    @FXML
    private TextField fldUsername;

    /**
     * Pole tekstowe do wprowadzenia hasła.
     */
    @FXML
    private PasswordField fldPassword;

    /**
     * Przycisk inicjujący proces logowania.
     */
    @FXML
    private Button btnLogin;

    /**
     * Etykieta wyświetlająca komunikaty związane z logowaniem.
     */
    @FXML
    private Label lblLogin;

    /**
     * Serwis obsługujący logikę związaną z użytkownikami.
     */
    private final UserService userService;

    /**
     * Konstruktor klasy LoginController.
     * Inicjalizuje instancję klasy UserService.
     */
    public LoginController() {
        userService = new UserService();
    }

    /**
     * Obsługuje proces logowania użytkownika.
     * Weryfikuje dane logowania i przekierowuje na główny ekran aplikacji po poprawnym logowaniu.
     * Wyświetla odpowiednie komunikaty w przypadku błędnych danych lub ich braku.
     *
     * @param actionEvent zdarzenie wywołane przez użytkownika, np. kliknięcie przycisku logowania.
     * @throws IOException w przypadku problemu z załadowaniem widoku głównego.
     */
    public void login(ActionEvent actionEvent) throws IOException {
        String username = fldUsername.getText();
        String password = fldPassword.getText();
        if (!(username.isEmpty() || password.isEmpty())) {
            if (userService.checkLogin(username, password)) {
                User user = userService.getUser(fldUsername.getText());
                lblLogin.setVisible(true);
                lblLogin.setTextFill(Paint.valueOf("#2fcf21"));
                lblLogin.setText("Logowanie się powiodło");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main-view.fxml"));
                Parent root = fxmlLoader.load();
                MainController mainController = fxmlLoader.getController();
                mainController.setCurrentUser(user);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                lblLogin.setVisible(true);
                lblLogin.setTextFill(Paint.valueOf("#cf2121"));
                lblLogin.setText("Błąd podczas logowania");
            }
        } else {
            lblLogin.setVisible(true);
            lblLogin.setTextFill(Paint.valueOf("#cf2121"));
            lblLogin.setText("Proszę wprowadzić dane");
        }
    }
}
