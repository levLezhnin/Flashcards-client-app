package com.llezhnin.flashcards.domain.mapper;

import com.llezhnin.flashcards.domain.UserFavouriteCategories;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFavouriteCategoriesMapper {

    public static UserFavouriteCategories fromJson(JSONObject jsonObject) {

        UserFavouriteCategories userFavouriteCategories = null;

        try {
            userFavouriteCategories = new UserFavouriteCategories(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("category_id")
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userFavouriteCategories;
    }
}
