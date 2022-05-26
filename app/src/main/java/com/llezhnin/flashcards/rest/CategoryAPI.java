package com.llezhnin.flashcards.rest;

import com.llezhnin.flashcards.domain.Category;

public interface CategoryAPI {

    void getAllCategories();

    void addCategoryToFavourites(int category_id);

    void addNewCategory(Category category);

    void removeCategoryFromFavourites(int category_id);
}
