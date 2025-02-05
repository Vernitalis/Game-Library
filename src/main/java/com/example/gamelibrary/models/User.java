package com.example.gamelibrary.models;

/**
 * Klasa reprezentująca użytkownika aplikacji.
 * Przechowuje informacje o użytkowniku, takie jak ID użytkownika, login, hasło, email oraz rola.
 */
public class User {

    /**
     * Unikalny identyfikator użytkownika.
     */
    private String user_id;

    /**
     * Login użytkownika.
     */
    private final String login;

    /**
     * Hasło użytkownika.
     */
    private final String password;

    /**
     * Adres email użytkownika.
     */
    private final String email;

    /**
     * Rola użytkownika w systemie (np. administrator, użytkownik).
     */
    private String role;

    /**
     * Konstruktor tworzący obiekt użytkownika z podstawowymi informacjami.
     *
     * @param login    login użytkownika.
     * @param password hasło użytkownika.
     * @param email    adres email użytkownika.
     */
    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    /**
     * Konstruktor tworzący obiekt użytkownika z pełnymi informacjami.
     *
     * @param user_id  unikalny identyfikator użytkownika.
     * @param login    login użytkownika.
     * @param password hasło użytkownika.
     * @param email    adres email użytkownika.
     * @param role     rola użytkownika w systemie.
     */
    public User(String user_id, String login, String password, String email, String role) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    /**
     * Pobiera unikalny identyfikator użytkownika.
     *
     * @return identyfikator użytkownika.
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Pobiera login użytkownika.
     *
     * @return login użytkownika.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Pobiera hasło użytkownika.
     *
     * @return hasło użytkownika.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Pobiera adres email użytkownika.
     *
     * @return adres email użytkownika.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Pobiera rolę użytkownika w systemie.
     *
     * @return rola użytkownika.
     */
    public String getRole() {
        return role;
    }

    /**
     * Zwraca tekstową reprezentację obiektu użytkownika.
     *
     * @return tekstowa reprezentacja użytkownika zawierająca ID, login, email i rolę.
     */
    @Override
    public String toString() {
        return "ID: (" + user_id + ")" +
                "\nNazwa użytkownika: " + login +
                "\nAdres email: " + email +
                "\nRola: " + role;
    }
}
