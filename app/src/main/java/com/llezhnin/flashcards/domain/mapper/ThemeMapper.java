package com.llezhnin.flashcards.domain.mapper;

import android.util.Log;

import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.domain.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThemeMapper {

    public static Theme fromJson(JSONObject jsonObject, JSONArray cards_in_theme) {

        Theme theme = null;
        ArrayList<Card> cards = new ArrayList<>();

        try {

            for (int i = 0; i < cards_in_theme.length(); i++) {
                JSONObject json_card = cards_in_theme.getJSONObject(i);
                Card card = CardMapper.fromJson(json_card);
                cards.add(card);
            }

            theme = new Theme(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    cards
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return theme;
    }
}
