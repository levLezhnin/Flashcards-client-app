package com.llezhnin.flashcards.domain;

public class UserFavouriteCategories {

    private int id;

    private int category_id;

    public UserFavouriteCategories(int id, int category_id) {
        this.id = id;
        this.category_id = category_id;
    }

    public UserFavouriteCategories(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public int getCategory_id() {
        return category_id;
    }
}
