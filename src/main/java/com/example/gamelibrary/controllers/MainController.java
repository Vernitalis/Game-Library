package com.example.gamelibrary.controllers;

import com.example.gamelibrary.models.Game;
import com.example.gamelibrary.models.Review;
import com.example.gamelibrary.models.User;
import com.example.gamelibrary.services.GameService;
import com.example.gamelibrary.services.ReviewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Klasa MainController jest odpowiedzialna za kontrolowanie interakcji użytkownika z aplikacją.
 * Zawiera pola do zarządzania widokami gier, recenzji, a także umożliwia dodawanie i edytowanie tych danych.
 */
public class MainController {

    /**
     * Obszar, w którym wyświetlane są różne elementy interfejsu.
     */
    @FXML
    private StackPane contentArea;

    /**
     * Widok listy gier w formie pionowej listy.
     */
    @FXML
    private VBox gameListView;

    /**
     * Pole tekstowe umożliwiające wyszukiwanie gier.
     */
    @FXML
    private TextField searchField;

    /**
     * Tabela, w której wyświetlane są gry.
     */
    @FXML
    private TableView<Game> gameTable;

    /**
     * Kolumna wyświetlająca identyfikatory gier.
     */
    @FXML
    private TableColumn<Game, String> idColumn;

    /**
     * Kolumna wyświetlająca nazwy gier.
     */
    @FXML
    private TableColumn<Game, String> nameColumn;

    /**
     * Kolumna wyświetlająca platformy gier.
     */
    @FXML
    private TableColumn<Game, String> platformColumn;

    /**
     * Kolumna wyświetlająca ceny gier.
     */
    @FXML
    private TableColumn<Game, String> priceColumn;

    /**
     * Kolumna wyświetlająca daty zakupu gier.
     */
    @FXML
    private TableColumn<Game, String> purchaseDateColumn;

    /**
     * Kolumna wyświetlająca deweloperów gier.
     */
    @FXML
    private TableColumn<Game, String> developerColumn;

    /**
     * Kolumna wyświetlająca aplikacje gier.
     */
    @FXML
    private TableColumn<Game, String> applicationColumn;

    /**
     * Widok formularza dodawania gry.
     */
    @FXML
    private VBox addGameForm;

    /**
     * Pole tekstowe do wprowadzania nazwy gry w formularzu dodawania.
     */
    @FXML
    private TextField gameNameField;

    /**
     * Pole tekstowe do wprowadzania platformy gry w formularzu dodawania.
     */
    @FXML
    private TextField gamePlatformField;

    /**
     * Pole tekstowe do wprowadzania ceny gry w formularzu dodawania.
     */
    @FXML
    private TextField gamePriceField;

    /**
     * Pole do wyboru daty zakupu gry w formularzu dodawania.
     */
    @FXML
    private DatePicker purchaseDatePicker;

    /**
     * Pole tekstowe do wprowadzania dewelopera gry w formularzu dodawania.
     */
    @FXML
    private TextField gameDevField;

    /**
     * Pole tekstowe do wprowadzania aplikacji gry w formularzu dodawania.
     */
    @FXML
    private TextField gameAppField;

    /**
     * Przycisk anulowania dodawania gry.
     */
    @FXML
    private Button gameAddCancelBtn;

    /**
     * Przycisk zapisywania dodanej gry.
     */
    @FXML
    private Button gameAddSaveBtn;

    /**
     * Przycisk do edytowania wybranej gry.
     */
    @FXML
    private Button editGameBtn;

    /**
     * Widok formularza edytowania gry.
     */
    @FXML
    private VBox editGameForm;

    /**
     * Pole tekstowe do wprowadzania nazwy gry w formularzu edytowania.
     */
    @FXML
    private TextField gameNameFieldEdit;

    /**
     * Pole tekstowe do wprowadzania platformy gry w formularzu edytowania.
     */
    @FXML
    private TextField gamePlatformFieldEdit;

    /**
     * Pole tekstowe do wprowadzania aplikacji gry w formularzu edytowania.
     */
    @FXML
    private TextField gameAppFieldEdit;

    /**
     * Pole tekstowe do wprowadzania dewelopera gry w formularzu edytowania.
     */
    @FXML
    private TextField gameDevFieldEdit;

    /**
     * Przycisk anulowania edytowania gry.
     */
    @FXML
    private Button gameEditCancelBtn;

    /**
     * Przycisk zapisywania edytowanej gry.
     */
    @FXML
    private Button gameEditSaveBtn;

    /**
     * Widok recenzji.
     */
    @FXML
    private VBox reviewsView;

    /**
     * Tabela, w której wyświetlane są recenzje.
     */
    @FXML
    private TableView<Review> reviewTable;

    /**
     * Kolumna wyświetlająca identyfikatory recenzji.
     */
    @FXML
    private TableColumn<Review, String> colReviewId;

    /**
     * Kolumna wyświetlająca autorów recenzji.
     */
    @FXML
    private TableColumn<Review, String> colReviewUser;

    /**
     * Kolumna wyświetlająca gry, które zostały ocenione w recenzjach.
     */
    @FXML
    private TableColumn<Review, String> colReviewGame;

    /**
     * Kolumna wyświetlająca oceny w recenzjach.
     */
    @FXML
    private TableColumn<Review, String> colReviewRating;

    /**
     * Kolumna wyświetlająca komentarze w recenzjach.
     */
    @FXML
    private TableColumn<Review, String> colReviewComment;

    /**
     * Kolumna wyświetlająca daty recenzji.
     */
    @FXML
    private TableColumn<Review, String> colReviewDate;

    /**
     * Widok formularza dodawania recenzji.
     */
    @FXML
    private VBox addReviewForm;

    /**
     * Pole tekstowe do wprowadzania oceny recenzji.
     */
    @FXML
    private TextField reviewRatingField;

    /**
     * Pole tekstowe do wprowadzania komentarza recenzji.
     */
    @FXML
    private TextArea reviewTextArea;

    /**
     * Etykieta wyświetlająca informację o dodawaniu recenzji.
     */
    @FXML
    private Label reviewAddLabel;

    /**
     * Przycisk zapisywania nowej recenzji.
     */
    @FXML
    private Button reviewSaveBtn;

    /**
     * Przycisk anulowania dodawania recenzji.
     */
    @FXML
    private Button reviewCancelBtn;

    /**
     * Widok formularza edytowania recenzji.
     */
    @FXML
    private VBox editReviewForm;

    /**
     * Pole tekstowe do wprowadzania oceny edytowanej recenzji.
     */
    @FXML
    private TextField reviewEditRatingField;

    /**
     * Pole tekstowe do edytowania komentarza recenzji.
     */
    @FXML
    private TextArea reviewEditTextArea;

    /**
     * Etykieta wyświetlająca informację o edytowaniu recenzji.
     */
    @FXML
    private Label reviewEditLabel;

    /**
     * Przycisk zapisywania edytowanej recenzji.
     */
    @FXML
    private Button reviewEditSaveBtn;

    /**
     * Przycisk anulowania edytowania recenzji.
     */
    @FXML
    private Button reviewEditCancelBtn;

    /**
     * Etykieta powitalna.
     */
    @FXML
    private Label welcomeLabel;

    /**
     * Widok powitalny.
     */
    @FXML
    private VBox welcomeVBox;

    /**
     * Etykieta wyświetlająca informacje o aktualnym użytkowniku.
     */
    @FXML
    private Label userInfoLabel;

    /**
     * Usługa zarządzająca grami.
     */
    private final GameService gameService = new GameService();

    /**
     * Wybrana gra.
     */
    private Game selectedGame;

    /**
     * Usługa zarządzająca recenzjami.
     */
    private final ReviewService reviewService = new ReviewService();

    /**
     * Wybrana recenzja.
     */
    private Review selectedReview;

    /**
     * Bieżący użytkownik.
     */
    private User currentUser;

    /**
     * Typy elementów (gra, recenzja).
     */
    enum Item {GAME, REVIEW}

    /**
     * Działania wykonywane na elementach (dodawanie gry, edytowanie gry, dodawanie recenzji, edytowanie recenzji).
     */
    enum Action {ADD_GAME, EDIT_GAME, ADD_REVIEW, EDIT_REVIEW}

    /**
     * Ustawia aktualnego użytkownika.
     *
     * @param user Obiekt użytkownika, który ma zostać ustawiony jako aktualny.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
        updateUI();
    }

    /**
     * Aktualizuje interfejs użytkownika w zależności od roli i stanu użytkownika.
     */
    private void updateUI() {
        userInfoLabel.setText("Zalogowany użytkownik: " + currentUser.getLogin());
        showView(null, "welcomeVBox");
        welcomeLabel.setText("Witaj w bibliotece " + currentUser.getLogin());
        if (currentUser.getRole().equals("admin")) {
            editGameBtn.setDisable(false);
        }
    }

    /**
     * Pobiera wybraną pozycję w tabeli na podstawie typu.
     *
     * @param item Typ, który określa, czy wybrano grę, czy recenzję.
     */
    private void getSelectedRow(Item item) {
        if (item == Item.GAME) {
            selectedGame = this.gameTable.getSelectionModel().getSelectedItem();
        } else if (item == Item.REVIEW) {
            selectedReview = this.reviewTable.getSelectionModel().getSelectedItem();
        }
    }

    /**
     * Ładuje listę gier z serwisu i wyświetla je w tabeli.
     */
    private void loadGames() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("game_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("platform"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("price"));
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("purchaseDate"));
        developerColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("developer"));
        applicationColumn.setCellValueFactory(new PropertyValueFactory<Game, String>("application"));

        List<Game> games = gameService.getAllGamesForUser(currentUser);
        ObservableList<Game> observableGames = FXCollections.observableArrayList(games);
        gameTable.setItems(observableGames);
    }

    /**
     * Ładuje listę recenzji z serwisu i wyświetla je w tabeli.
     */
    private void loadReviews() {
        colReviewId.setCellValueFactory(new PropertyValueFactory<Review, String>("reviewID"));
        colReviewUser.setCellValueFactory(new PropertyValueFactory<Review, String>("author"));
        colReviewGame.setCellValueFactory(new PropertyValueFactory<Review, String>("gameName"));
        colReviewRating.setCellValueFactory(new PropertyValueFactory<Review, String>("rating"));
        colReviewComment.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
        colReviewDate.setCellValueFactory(new PropertyValueFactory<Review, String>("date"));

        List<Review> reviews = reviewService.getAllReviews();
        ObservableList<Review> observableReviews = FXCollections.observableArrayList(reviews);
        reviewTable.setItems(observableReviews);
    }

    /**
     * Wyświetla informacje o profilu użytkownika w oknie alertu.
     *
     * @param event Wydarzenie związane z akcją użytkownika.
     */
    @FXML
    public void showUserProfile(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profil użytkownika");
        alert.setHeaderText("Informacje o profilu");
        alert.setContentText(currentUser.toString());
        alert.showAndWait();
    }

    /**
     * Przeprowadza proces wylogowania użytkownika i ładuje ekran startowy.
     *
     * @param event Wydarzenie związane z akcją wylogowania.
     * @throws IOException Jeśli wystąpi błąd przy ładowaniu ekranu.
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wylogowanie");
        alert.setHeaderText("Potwierdzenie wylogowania");
        alert.setContentText("Czy na pewno chcesz się wylogować?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentUser = null;
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/start-view.fxml"));
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Zamyka aplikację po potwierdzeniu przez użytkownika.
     *
     * @param event Wydarzenie związane z akcją zamknięcia aplikacji.
     */
    @FXML
    public void exitApplication(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Zamykanie aplikacji");
        alert.setHeaderText("Potwierdzenie zamknięcia");
        alert.setContentText("Czy na pewno chcesz zamknąć aplikację?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Wyświetla określoną widok (np. lista gier, formularz dodawania gry).
     *
     * @param event    Wydarzenie związane z akcją użytkownika.
     * @param viewName Nazwa widoku, który ma zostać wyświetlony.
     */
    @FXML
    public void showView(ActionEvent event, String viewName) {
        gameListView.setVisible("gameListView".equals(viewName));
        addGameForm.setVisible("addGameForm".equals(viewName));
        editGameForm.setVisible("editGameForm".equals(viewName));
        reviewsView.setVisible("reviewsView".equals(viewName));
        addReviewForm.setVisible("addReviewForm".equals(viewName));
        editReviewForm.setVisible("editReviewForm".equals(viewName));
        welcomeVBox.setVisible("welcomeVBox".equals(viewName));

        if (viewName.equals("gameListView")) {
            loadGames();
        }
        if (viewName.equals("reviewsView")) {
            loadReviews();
        }
    }

    /**
     * Wyświetla formularz dodawania gry.
     *
     * @param event Wydarzenie związane z akcją użytkownika.
     */
    @FXML
    public void showAddGameForm(ActionEvent event) {
        showView(event, "addGameForm");
    }

    /**
     * Wyświetla widok recenzji.
     *
     * @param event Wydarzenie związane z akcją użytkownika.
     */
    public void showReviews(ActionEvent event) {
        showView(event, "reviewsView");
    }

    /**
     * Wyświetla listę gier.
     *
     * @param event Wydarzenie związane z akcją użytkownika.
     */
    public void showGameList(ActionEvent event) {
        showView(event, "gameListView");
    }

    /**
     * Zapisuje nową grę do bazy danych po walidacji danych.
     *
     * @param event Wydarzenie związane z akcją zapisu gry.
     */
    @FXML
    public void saveGame(ActionEvent event) {
        String name = gameNameField.getText();
        String platform = gamePlatformField.getText();
        String developer = gameDevField.getText();
        String price = gamePriceField.getText();
        String application = gameAppField.getText();
        LocalDate purchaseDateLocal = purchaseDatePicker.getValue();
        String purchaseDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(purchaseDateLocal);

        if (name.isEmpty() || platform.isEmpty() || developer.isEmpty() || price.isEmpty() || application.isEmpty() || purchaseDate.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }
        try {
            float parsedPrice = Float.parseFloat(price);
            if (parsedPrice < 0) {
                showAlert(Alert.AlertType.WARNING, "Błąd", "Cena nie może być ujemna.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Cena musi być liczbą");
            return;
        }


        Game newGame = new Game(name, platform);
        gameService.addGame(newGame, developer, price, application, purchaseDate, currentUser.getUser_id());
        clearInputs(Action.ADD_GAME);
        showView(event, "gameListView");
    }

    /**
     * Anuluje dodawanie gry i powraca do listy gier.
     *
     * @param event Wydarzenie związane z akcją anulowania.
     */
    @FXML
    public void cancelAddGame(ActionEvent event) {
        clearInputs(Action.ADD_GAME);
        showView(event, "gameListView");
    }

    /**
     * Usuwa wybraną grę po potwierdzeniu przez użytkownika.
     *
     * @param event Wydarzenie związane z akcją usuwania gry.
     */
    @FXML
    public void deleteSelectedGame(ActionEvent event) {
        getSelectedRow(Item.GAME);
        if (selectedGame == null) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Nie wybrano gry do usunięcia.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Usuwanie gry");
        alert.setContentText("Czy na pewno chcesz usunąć " + selectedGame.getName() + "? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            gameService.deleteGame(selectedGame, currentUser.getUser_id());
            loadGames();
        }
        selectedGame = null;
    }

    /**
     * Wyświetla formularz edycji wybranej gry.
     *
     * @param event Wydarzenie związane z akcją edycji gry.
     */
    @FXML
    public void editSelectedGame(ActionEvent event) {
        getSelectedRow(Item.GAME);
        if (selectedGame == null) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Nie wybrano gry do edycji.");
            return;
        }
        showView(event, "editGameForm");
        gameNameFieldEdit.setText(selectedGame.getName());
        gamePlatformFieldEdit.setText(selectedGame.getPlatform());
        gameDevFieldEdit.setText(selectedGame.getDeveloper());
        gameAppFieldEdit.setText(selectedGame.getApplication());
    }

    /**
     * Zapisuje zmienione dane gry po walidacji.
     *
     * @param event Wydarzenie związane z akcją zapisu zmienionej gry.
     */
    @FXML
    public void saveUpdatedGame(ActionEvent event) {
        String newName = gameNameFieldEdit.getText();
        String newPlatform = gamePlatformFieldEdit.getText();
        String newDeveloper = gameDevFieldEdit.getText();
        String newApp = gameAppFieldEdit.getText();

        if (newName.isEmpty() || newPlatform.isEmpty() || newDeveloper.isEmpty() || newApp.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }

        gameService.updateGame(selectedGame, newName, newPlatform, newDeveloper, newApp);
        showView(event, "gameListView");
        selectedGame = null;
    }

    /**
     * Anuluje edycję gry i powraca do listy gier.
     *
     * @param event Wydarzenie związane z akcją anulowania edycji gry.
     */
    @FXML
    public void cancelEditGame(ActionEvent event) {
        clearInputs(Action.EDIT_GAME);
        showView(event, "gameListView");
    }

    /**
     * Rozpoczyna proces dodawania recenzji dla wybranej gry.
     *
     * @param event Wydarzenie związane z akcją dodawania recenzji.
     */
    @FXML
    public void addReview(ActionEvent event) {
        getSelectedRow(Item.GAME);
        if (selectedGame == null) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Nie wybrano gry do recenzji.");
            return;
        }
        if (reviewService.reviewAlreadyExists(selectedGame, currentUser.getUser_id())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText("Nie można dodać recenzji");
            alert.setContentText("Użytkownik posiada już recenzję dla wybranej gry");
            alert.showAndWait();
        } else {
            showView(event, "addReviewForm");
            reviewAddLabel.setText("Dodawanie recenzji dla gry: " + selectedGame.getName());
        }
    }

    /**
     * Zapisuje recenzję do bazy danych po walidacji danych.
     *
     * @param event Wydarzenie związane z akcją zapisu recenzji.
     */
    @FXML
    public void saveReview(ActionEvent event) {
        String rating = reviewRatingField.getText();
        String comment = reviewTextArea.getText();

        if (rating.isEmpty() || comment.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }
        try {
            float parsedRating = Float.parseFloat(rating);
            if (parsedRating < 0) {
                showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena nie może być ujemna.");
                return;
            } else if (parsedRating > 10) {
                showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena nie może być wyższa niż 10.0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena musi być liczbą");
            return;
        }

        reviewService.addReview(selectedGame, currentUser.getUser_id(), rating, comment);
        clearInputs(Action.ADD_REVIEW);
        showView(event, "reviewsView");
        selectedGame = null;
    }

    /**
     * Anuluje dodawanie recenzji i powraca do listy gier.
     *
     * @param event Wydarzenie związane z akcją anulowania dodawania recenzji.
     */
    @FXML
    public void cancelAddReview(ActionEvent event) {
        clearInputs(Action.ADD_REVIEW);
        showView(event, "gameListView");
        selectedGame = null;
    }

    /**
     * Wyświetla formularz edycji wybranej recenzji.
     *
     * @param event Wydarzenie związane z akcją edycji recenzji.
     */
    @FXML
    public void editSelectedReview(ActionEvent event) {
        getSelectedRow(Item.REVIEW);
        if (selectedReview == null) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Nie wybrano recenzji do edycji.");
            return;
        }
        if (reviewService.reviewBelongsToUserID(currentUser.getUser_id(), selectedReview.getReviewID())) {
            showView(event, "editReviewForm");
            reviewEditLabel.setText("Edytowanie recenzji dla: " + selectedReview.getGameName());
            reviewEditRatingField.setText(selectedReview.getRating());
            reviewEditTextArea.setText(selectedReview.getComment());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setHeaderText("Edytowanie recenzji");
            alert.setContentText("Recenzja nie należy do użytkownika");
            alert.showAndWait();
        }
    }

    /**
     * Anuluje edycję recenzji i powraca do widoku recenzji.
     *
     * @param event Wydarzenie związane z akcją anulowania edycji recenzji.
     */
    @FXML
    public void cancelEditReview(ActionEvent event) {
        clearInputs(Action.EDIT_REVIEW);
        showView(event, "reviewsView");
        selectedGame = null;
    }

    /**
     * Zapisuje zmienioną recenzję do bazy danych po walidacji.
     *
     * @param event Wydarzenie związane z akcją zapisu zmienionej recenzji.
     */
    @FXML
    public void saveUpdatedReview(ActionEvent event) {
        String rating = reviewEditRatingField.getText();
        String comment = reviewEditTextArea.getText();

        if (rating.isEmpty() || comment.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }
        try {
            float parsedRating = Float.parseFloat(rating);
            if (parsedRating < 0) {
                showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena nie może być ujemna.");
                return;
            } else if (parsedRating > 10) {
                showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena nie może być wyższa niż 10.0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Ocena musi być liczbą");
            return;
        }
        reviewService.updateReview(selectedReview, rating, comment);
        showView(event, "reviewsView");
        selectedReview = null;
    }

    /**
     * Usuwa wybraną recenzję po potwierdzeniu przez użytkownika.
     *
     * @param event Wydarzenie związane z akcją usuwania recenzji.
     */
    @FXML
    public void deleteSelectedReview(ActionEvent event) {
        getSelectedRow(Item.REVIEW);
        if (selectedReview == null) {
            showAlert(Alert.AlertType.WARNING, "Błąd", "Nie wybrano recenzji do usunięcia.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Usuwanie recenzji");
        alert.setContentText("Czy na pewno chcesz usunąć recenzję " + selectedReview.getReviewID() + "? ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (reviewService.reviewBelongsToUserID(currentUser.getUser_id(), selectedReview.getReviewID())) {
                reviewService.deleteReview(selectedReview, currentUser.getUser_id());
            } else {
                Alert innerAlert = new Alert(Alert.AlertType.INFORMATION);
                innerAlert.setTitle("Informacja");
                innerAlert.setHeaderText("Nie można usunąć recenzji");
                innerAlert.setContentText("Recenzja nie należy do użytkownika");
                innerAlert.showAndWait();
            }
            loadReviews();
        }
        selectedReview = null;
    }

    /**
     * Czyści pola formularza na podstawie wykonywanej akcji.
     *
     * @param action Typ akcji, której dotyczy czyszczenie formularza.
     */
    private void clearInputs(Action action) {
        if (action == Action.ADD_GAME) {
            gameNameField.clear();
            gamePlatformField.clear();
            gamePriceField.clear();
            purchaseDatePicker.getEditor().clear();
            gameDevField.clear();
            gameAppField.clear();
        } else if (action == Action.EDIT_GAME) {
            gameNameFieldEdit.clear();
            gamePlatformFieldEdit.clear();
            gameDevFieldEdit.clear();
            gameAppFieldEdit.clear();
        } else if (action == Action.ADD_REVIEW) {
            reviewRatingField.clear();
            reviewTextArea.clear();
        } else if (action == Action.EDIT_REVIEW) {
            reviewEditRatingField.clear();
            reviewEditTextArea.clear();
        }
    }

    /**
     * Wyświetla alert z określonym typem, tytułem i wiadomością.
     *
     * @param alertType Typ alertu.
     * @param title     Tytuł alertu.
     * @param message   Treść wiadomości alertu.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
