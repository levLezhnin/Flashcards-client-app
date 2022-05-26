package com.llezhnin.flashcards.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Theme implements Serializable {

    private int id;

    private String name;

    private List<Card> cards;

    public Theme(int id, String name, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
    }

    public Theme(int id, String name) {
        this.id = id;
        this.name = name;
        cards = new ArrayList<>();
    }

    public Theme(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    public Theme(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
