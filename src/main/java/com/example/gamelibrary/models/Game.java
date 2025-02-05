package com.example.gamelibrary.models;

/**
 * Klasa reprezentująca grę w bibliotece gier.
 * Odpowiada za przechowywanie danych o grach, takich jak ID, nazwa, platforma, cena, data zakupu, deweloper oraz aplikacja.
 */
public class Game {

    /**
     * Unikalny identyfikator gry.
     */
    private String game_id;

    /**
     * Nazwa gry.
     */
    private String name;

    /**
     * Platforma, na której gra jest dostępna.
     */
    private String platform;

    /**
     * Cena gry.
     */
    private String price;

    /**
     * Data zakupu gry.
     */
    private String purchaseDate;

    /**
     * Deweloper gry.
     */
    private String developer;

    /**
     * Aplikacja powiązana z grą.
     */
    private String application;

    /**
     * Konstruktor tworzący obiekt gry z wszystkimi parametrami.
     *
     * @param game_id      unikalny identyfikator gry.
     * @param name         nazwa gry.
     * @param platform     platforma gry.
     * @param price        cena gry.
     * @param purchaseDate data zakupu gry.
     * @param developer    deweloper gry.
     * @param application  aplikacja powiązana z grą.
     */
    public Game(String game_id, String name, String platform, String price, String purchaseDate, String developer, String application) {
        this.game_id = game_id;
        this.name = name;
        this.platform = platform;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.developer = developer;
        this.application = application;
    }

    /**
     * Konstruktor tworzący obiekt gry z podstawowymi informacjami.
     *
     * @param game_id  unikalny identyfikator gry.
     * @param name     nazwa gry.
     * @param platform platforma gry.
     */
    public Game(String game_id, String name, String platform) {
        this.game_id = game_id;
        this.name = name;
        this.platform = platform;
    }

    /**
     * Konstruktor tworzący obiekt gry z nazwą i platformą.
     *
     * @param name     nazwa gry.
     * @param platform platforma gry.
     */
    public Game(String name, String platform) {
        this.name = name;
        this.platform = platform;
    }

    /**
     * Pobiera unikalny identyfikator gry.
     *
     * @return unikalny identyfikator gry.
     */
    public String getGame_id() {
        return game_id;
    }

    /**
     * Pobiera nazwę gry.
     *
     * @return nazwa gry.
     */
    public String getName() {
        return name;
    }

    /**
     * Pobiera platformę gry.
     *
     * @return platforma gry.
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Pobiera cenę gry.
     *
     * @return cena gry.
     */
    public String getPrice() {
        return price;
    }

    /**
     * Pobiera datę zakupu gry.
     *
     * @return data zakupu gry.
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Pobiera dewelopera gry.
     *
     * @return deweloper gry.
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     * Pobiera aplikację powiązaną z grą.
     *
     * @return aplikacja powiązana z grą.
     */
    public String getApplication() {
        return application;
    }

    /**
     * Ustawia unikalny identyfikator gry.
     *
     * @param game_id unikalny identyfikator gry.
     */
    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    /**
     * Ustawia nazwę gry.
     *
     * @param name nazwa gry.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ustawia platformę gry.
     *
     * @param platform platforma gry.
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Ustawia cenę gry.
     *
     * @param price cena gry.
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Ustawia datę zakupu gry.
     *
     * @param purchaseDate data zakupu gry.
     */
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Ustawia dewelopera gry.
     *
     * @param developer deweloper gry.
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     * Ustawia aplikację powiązaną z grą.
     *
     * @param application aplikacja powiązana z grą.
     */
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * Zwraca tekstową reprezentację obiektu gry.
     *
     * @return tekstowa reprezentacja obiektu gry.
     */
    @Override
    public String toString() {
        return "Game{" +
                "game_id='" + game_id + '\'' +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", price='" + price + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", developer='" + developer + '\'' +
                ", application='" + application + '\'' +
                '}';
    }
}
