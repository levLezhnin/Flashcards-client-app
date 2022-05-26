package com.llezhnin.flashcards.domain.mapper;

import com.llezhnin.flashcards.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {

    public static User fromJson(JSONObject jsonObject) {

        User user = null;

        try {

            user = new User(jsonObject.getInt("id"),
                    jsonObject.getString("nickname"),
                    jsonObject.getString("email"),
                    jsonObject.getString("password"),
                    null,
                    null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
