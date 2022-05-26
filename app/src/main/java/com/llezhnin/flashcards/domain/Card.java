package com.llezhnin.flashcards.domain;

import java.io.Serializable;

public class Card implements Serializable {

    private int id;

    private String key;

    private String value;

    public Card(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Card(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
