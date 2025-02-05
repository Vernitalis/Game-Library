package com.example.gamelibrary.models;

/**
 * Klasa reprezentująca recenzję gry.
 * Przechowuje informacje o recenzji, takie jak ID recenzji, autor, nazwa gry, ocena, komentarz oraz data.
 */
public class Review {

    /**
     * Unikalny identyfikator recenzji.
     */
    private String reviewID;

    /**
     * Autor recenzji.
     */
    private String author;

    /**
     * Nazwa gry, której dotyczy recenzja.
     */
    private String gameName;

    /**
     * Ocena przypisana w recenzji.
     */
    private String rating;

    /**
     * Komentarz zawarty w recenzji.
     */
    private String comment;

    /**
     * Data dodania recenzji.
     */
    private String date;

    /**
     * Konstruktor tworzący obiekt recenzji z pełnymi informacjami.
     *
     * @param reviewID unikalny identyfikator recenzji.
     * @param author   autor recenzji.
     * @param gameName nazwa gry, której dotyczy recenzja.
     * @param rating   ocena przypisana w recenzji.
     * @param comment  komentarz zawarty w recenzji.
     * @param date     data dodania recenzji.
     */
    public Review(String reviewID, String author, String gameName, String rating, String comment, String date) {
        this.reviewID = reviewID;
        this.author = author;
        this.gameName = gameName;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    /**
     * Konstruktor tworzący obiekt recenzji z podstawowymi informacjami.
     *
     * @param rating  ocena przypisana w recenzji.
     * @param comment komentarz zawarty w recenzji.
     */
    public Review(String rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Pobiera ocenę przypisaną w recenzji.
     *
     * @return ocena recenzji.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Ustawia ocenę recenzji.
     *
     * @param rating ocena recenzji.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Pobiera komentarz zawarty w recenzji.
     *
     * @return komentarz recenzji.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Ustawia komentarz recenzji.
     *
     * @param comment komentarz recenzji.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Pobiera datę dodania recenzji.
     *
     * @return data recenzji.
     */
    public String getDate() {
        return date;
    }

    /**
     * Ustawia datę dodania recenzji.
     *
     * @param date data recenzji.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Pobiera autora recenzji.
     *
     * @return autor recenzji.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Ustawia autora recenzji.
     *
     * @param author autor recenzji.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Pobiera nazwę gry, której dotyczy recenzja.
     *
     * @return nazwa gry.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Ustawia nazwę gry, której dotyczy recenzja.
     *
     * @param gameName nazwa gry.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Pobiera unikalny identyfikator recenzji.
     *
     * @return identyfikator recenzji.
     */
    public String getReviewID() {
        return reviewID;
    }

    /**
     * Ustawia unikalny identyfikator recenzji.
     *
     * @param reviewID identyfikator recenzji.
     */
    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }
}
