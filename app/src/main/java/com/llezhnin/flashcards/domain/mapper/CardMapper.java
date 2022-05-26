package com.llezhnin.flashcards.domain.mapper;

import com.llezhnin.flashcards.domain.Card;

import org.json.JSONException;
import org.json.JSONObject;

public class CardMapper {

    public static Card fromJson(JSONObject jsonObject) {

        Card card = null;

        try {
        card = new Card(jsonObject.getInt("id"),
                jsonObject.getString("key"),
                jsonObject.getString("value"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return card;
    }
}
