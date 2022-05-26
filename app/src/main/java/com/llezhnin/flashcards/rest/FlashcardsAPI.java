package com.llezhnin.flashcards.rest;

public interface FlashcardsAPI {

    void fill();

    void fillThemesInCategory(int category_id);

    void fillCardsInTheme(int theme_id);
}
