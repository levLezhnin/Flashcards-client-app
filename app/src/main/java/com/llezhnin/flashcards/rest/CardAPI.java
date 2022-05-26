package com.llezhnin.flashcards.rest;

import com.llezhnin.flashcards.domain.Card;

public interface CardAPI {

    void addCardIntoTheme(Card card);

    void updateCard(int card_id, String key, String value);

    void getAllCardsByThemeId();

    void deleteCard(int card_id);
}
