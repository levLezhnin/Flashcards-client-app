package com.llezhnin.flashcards.domain.mapper;

import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.domain.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryMapper {

    public static Category fromJson(JSONObject jsonObject, JSONArray themes_in_category) {

        Category category = null;

        ArrayList<Theme> themes = new ArrayList<>();
        try {
            for (int i = 0; i < themes_in_category.length(); i++) {
                JSONObject json_theme = themes_in_category.getJSONObject(i);
                JSONArray cards = json_theme.getJSONArray("cardDTOList");
                Theme theme = ThemeMapper.fromJson(json_theme, cards);
                themes.add(theme);
            }

            category = new Category(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    themes
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return category;
    }
}
