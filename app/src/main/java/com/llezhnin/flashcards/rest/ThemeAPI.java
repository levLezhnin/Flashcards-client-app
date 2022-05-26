package com.llezhnin.flashcards.rest;

import com.llezhnin.flashcards.domain.Theme;

public interface ThemeAPI {

    void getAllThemesByCategoryId();

    void getAllThemesByUserId();

    void addNewThemeIntoCategory(Theme theme);

    void updateTheme(int theme_id, String name);

    void deleteTheme(int theme_id);
}
