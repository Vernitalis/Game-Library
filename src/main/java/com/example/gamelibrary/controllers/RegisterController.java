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
 * Kontroler odpowiedzialny za rejestrowanie nowych użytkowników w aplikacji.
 */
public class RegisterController {

    /**
     * Pole tekstowe do wprowadzenia nazwy użytkownika.
     */
    @FXML
    private TextField fldUsername;

    /**
     * Pole tekstowe do wprowadzenia adresu e-mail.
     */
    @FXML
    private TextField fldEmail;

    /**
     * Pole tekstowe do wprowadzenia hasła.
     */
    @FXML
    private PasswordField fldPassword;

    /**
     * Przycisk inicjujący proces rejestracji.
     */
    @FXML
    private Button btnRegister;

    /**
     * Przycisk powrotu do menu głównego.
     */
    @FXML
    private Button btnBack;

    /**
     * Etykieta wyświetlająca komunikaty związane z rejestracją.
     */
    @FXML
    private Label lblRegister;

    /**
     * Serwis obsługujący logikę związaną z użytkownikami.
     */
    private UserService userService = new UserService();

    /**
     * Waliduje, czy podane pola tekstowe nie są puste ani null.
     *
     * @param fields lista pól tekstowych do walidacji.
     * @return true, jeśli wszystkie pola są wypełnione, w przeciwnym razie false.
     */
    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obsługuje proces rejestracji nowego użytkownika.
     * Sprawdza, czy wszystkie pola są poprawnie wypełnione,
     * a następnie dodaje użytkownika do bazy danych.
     * Wyświetla odpowiednie komunikaty w przypadku powodzenia lub błędów.
     */
    public void register() {
        if (validateFields(fldUsername.getText(), fldEmail.getText(), fldPassword.getText())) {
            if (userService.addUser(new User(fldUsername.getText(), fldPassword.getText(), fldEmail.getText()))) {
                lblRegister.setVisible(true);
                lblRegister.setTextFill(Paint.valueOf("#2fcf21"));
                lblRegister.setText("Użytkownik został zarejestrowany");
            } else {
                lblRegister.setVisible(true);
                lblRegister.setTextFill(Paint.valueOf("#cf2121"));
                lblRegister.setText("Błąd podczas rejestracji");
            }
        } else {
            lblRegister.setVisible(true);
            lblRegister.setTextFill(Paint.valueOf("#cf2121"));
            lblRegister.setText("Nie wszystkie pola są poprawnie wypełnione");
        }
    }

    /**
     * Przekierowuje użytkownika z powrotem do menu głównego.
     *
     * @param actionEvent zdarzenie wywołane przez użytkownika, np. kliknięcie przycisku powrotu.
     * @throws IOException w przypadku problemu z załadowaniem widoku startowego.
     */
    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
