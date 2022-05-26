package com.llezhnin.flashcards.domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;

    private String name;

    private List<Theme> themes_in_category;

    public Category(int id, String name, List<Theme> themes_in_category) {
        this.id = id;
        this.name = name;
        this.themes_in_category = themes_in_category;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        themes_in_category = new ArrayList<>();
    }

    public Category(String name, List<Theme> themes_in_category) {
        this.name = name;
        this.themes_in_category = themes_in_category;
    }

    public Category(String name) {
        this.name = name;
        themes_in_category = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Theme> getThemes_in_category() {
        return themes_in_category;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + "\" }";
    }
}
