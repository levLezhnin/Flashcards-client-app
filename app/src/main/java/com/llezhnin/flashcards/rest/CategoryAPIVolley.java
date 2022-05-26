package com.llezhnin.flashcards.rest;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.llezhnin.flashcards.Constants;
import com.llezhnin.flashcards.activities.AdapterActivity;
import com.llezhnin.flashcards.activities.CategoriesActivity;
import com.llezhnin.flashcards.activities.FindCategoriesActivity;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.domain.mapper.CategoryMapper;
import com.llezhnin.flashcards.nodb.NoDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CategoryAPIVolley implements CategoryAPI {

    public static final String API_TEST = "API_TEST";
    private final Context context;
    private final String url = Constants.BASE_URL;
    public static int user_id;

    private Response.ErrorListener errorListener = error -> Log.d(API_TEST, error.toString());

    public CategoryAPIVolley(Context context) {
        this.context = context;
        user_id = Constants.USER_ID;
    }

    @Override
    public void getAllCategories() {
        String url = this.url + "/category/all";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        NoDB.CATEGORIES.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                JSONArray jsonThemes = jsonObject.getJSONArray("themes_in_category");
                                Category category = CategoryMapper.fromJson(jsonObject, jsonThemes);
                                NoDB.CATEGORIES.add(category);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ((AdapterActivity)context).updateAdapter();
                        Log.d(API_TEST + "getAllCategories", String.valueOf(NoDB.CATEGORIES));
                    }
                },
                errorListener
        );

        requestQueue.add(request);
    }

    @Override
    public void addCategoryToFavourites(int category_id) {

        boolean isCategoryIdNew = true;

        for (Category c : NoDB.CATEGORIES) {
            if(c.getId() == category_id) {
                isCategoryIdNew = false;
                break;
            }
        }

        if(isCategoryIdNew) {

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            String url = this.url + "/user/" + user_id + "/add_to_favourites/" + category_id;

            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    response -> {
                        Log.d(API_TEST + "added to fav", response);
                        getAllCategories();
                    },
                    errorListener
            );

            requestQueue.add(request);
        } else {
            getAllCategories();
        }
    }

    @Override
    public void addNewCategory(Category category) {
        String url = this.url + "/category/new/";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.d(API_TEST + "addNewCategory ", response);
                    ((AdapterActivity) context).updateAdapter();
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("name", category.getName());

                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void removeCategoryFromFavourites(int category_id) {
        String url = this.url + "/user/"+user_id+"/remove_from_favourites/"+category_id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                response -> {
                    Log.d(API_TEST +" remove from fav", response);
                    FlashcardsAPIVolley.user_id = user_id;
                    new FlashcardsAPIVolley(context).fill();
                },
                errorListener
        );
        requestQueue.add(request);
    }
}
