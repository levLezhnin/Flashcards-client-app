package com.llezhnin.flashcards.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;

    private String nickname;

    private String email;

    private String password;

    private List<Theme> themes;

    private List<UserFavouriteCategories> favourite_categories;

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public List<UserFavouriteCategories> getFavourite_categories() {
        return favourite_categories;
    }

    public User(int id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        themes = new ArrayList<>();
        favourite_categories = new ArrayList<>();
    }

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        themes = new ArrayList<>();
        favourite_categories = new ArrayList<>();
    }

    public User(int id, String nickname, String email, String password, List<Theme> themes, List<UserFavouriteCategories> favourite_categories) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.themes = themes;
        this.favourite_categories = favourite_categories;
    }

    public User(String nickname, String email, String password, List<Theme> themes, List<UserFavouriteCategories> favourite_categories) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.themes = themes;
        this.favourite_categories = favourite_categories;
    }
}
