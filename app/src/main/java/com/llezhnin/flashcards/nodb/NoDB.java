package com.llezhnin.flashcards.nodb;

import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.domain.Theme;

import java.util.ArrayList;
import java.util.List;

public class NoDB {

    private NoDB() {}

    public static final List<Category> CATEGORIES = new ArrayList<>();
    public static final List<Theme> THEMES = new ArrayList<>();
    public static final List<Card> CARDS = new ArrayList<>();

}
