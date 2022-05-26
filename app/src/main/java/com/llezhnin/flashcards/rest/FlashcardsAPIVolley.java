package com.llezhnin.flashcards.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.llezhnin.flashcards.Constants;
import com.llezhnin.flashcards.activities.AdapterActivity;
import com.llezhnin.flashcards.activities.CategoriesActivity;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.domain.mapper.CategoryMapper;
import com.llezhnin.flashcards.nodb.NoDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlashcardsAPIVolley implements FlashcardsAPI {

    public static final String API_TEST = "API_TEST";
    private final Context context;
    private final String url = Constants.BASE_URL;
    public static int user_id;

    private Response.ErrorListener errorListener = error -> Log.d(API_TEST, error.toString());


    public FlashcardsAPIVolley(Context context) {
        this.context = context;
        user_id = Constants.USER_ID;
    }

    @Override
    public void fill() {

        String url = this.url + "/user/" + user_id + "/get_favourite_categories";

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(API_TEST + "fill(): "+ response.toString());
                        NoDB.CATEGORIES.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONArray json_themes = jsonObject.getJSONArray("themes_in_category");
                                Category category = CategoryMapper.fromJson(jsonObject, json_themes);
                                NoDB.CATEGORIES.add(category);
                            }
                            ((AdapterActivity) context).updateAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );
        requestQueue.add(jsonArrayRequest);
    }
    @Override
    public void fillThemesInCategory(int category_id) {
        NoDB.THEMES.clear();
        fill();
        Category category = NoDB.CATEGORIES.get(category_id);
        NoDB.THEMES.addAll(category.getThemes_in_category());
    }

    @Override
    public void fillCardsInTheme(int theme_id) {
        NoDB.CARDS.clear();
        fill();
        Theme theme = NoDB.THEMES.get(theme_id);
        NoDB.CARDS.addAll(theme.getCards());
    }
}
