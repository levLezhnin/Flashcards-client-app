package com.llezhnin.flashcards.rest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.llezhnin.flashcards.Constants;
import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.activities.AdapterActivity;
import com.llezhnin.flashcards.activities.UserThemesActivity;
import com.llezhnin.flashcards.activities.CardActivity;
import com.llezhnin.flashcards.activities.ThemeActivity;
import com.llezhnin.flashcards.adapter.ThemeAdapter;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.domain.mapper.ThemeMapper;
import com.llezhnin.flashcards.fragments.AddCardFragment;
import com.llezhnin.flashcards.fragments.ChangeCardFragment;
import com.llezhnin.flashcards.fragments.ChangeThemeFragment;
import com.llezhnin.flashcards.nodb.NoDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThemeAPIVolley implements ThemeAPI {

    public static final String API_TEST = "API_TEST";
    public static final String warning = "You cannot change other's themes and cards!";
    private final Context context;
    private final String url = Constants.BASE_URL;
    public static int user_id;
    public static int category_id;

    private Response.ErrorListener errorListener = error -> Log.d(API_TEST, error.toString());

    public ThemeAPIVolley(Context context) {
        this.context = context;
        user_id = Constants.USER_ID;
    }

    @Override
    public void getAllThemesByCategoryId() {
        NoDB.THEMES.clear();
        String url = this.url + "/category/" + category_id + "/themes";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        NoDB.THEMES.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject theme = response.getJSONObject(i);
                                JSONArray cards = theme.getJSONArray("cardDTOList");
                                NoDB.THEMES.add(ThemeMapper.fromJson(theme, cards));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ((AdapterActivity) context).updateAdapter();
                    }
                },
                errorListener
        );
        requestQueue.add(request);
    }

    @Override
    public void getAllThemesByUserId() {
        NoDB.THEMES.clear();

        String url = this.url + "/user/" + user_id + "/themes";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        NoDB.THEMES.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject theme = response.getJSONObject(i);
                                JSONArray cards = theme.getJSONArray("cardDTOList");
                                NoDB.THEMES.add(ThemeMapper.fromJson(theme, cards));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ((AdapterActivity) context).updateAdapter();
                    }
                },
                errorListener
        );
        requestQueue.add(request);
    }

    public void onAddCardFragment(int theme_id, CardActivity context, AddCardFragment addCardFragment) {
        String url_to_check_user = this.url + "/theme/" + theme_id + "/user";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request_to_check = new StringRequest(
                Request.Method.GET,
                url_to_check_user,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isCorrect = (Constants.USER_ID == jsonObject.getInt("id"));
                        if (isCorrect) {
                            context.getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fl_card_main, addCardFragment)
                                    .commit();
                        } else {
                            Toast.makeText(context, warning, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );

        requestQueue.add(request_to_check);
    }

    public void onChangeCardFragment(int theme_id, CardActivity context, ChangeCardFragment changeCardFragment) {
        String url_to_check_user = this.url + "/theme/" + theme_id + "/user";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request_to_check = new StringRequest(
                Request.Method.GET,
                url_to_check_user,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isCorrect = (Constants.USER_ID == jsonObject.getInt("id"));
                        if (isCorrect) {
                            context.getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fl_card_main, changeCardFragment)
                                    .commit();
                        } else {
                            Toast.makeText(context,  warning, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );

        requestQueue.add(request_to_check);
    }

    @Override
    public void addNewThemeIntoCategory(Theme theme) {
        String url = this.url + "/theme/new/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    getAllThemesByCategoryId();
                    ((AdapterActivity) context).updateAdapter();
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("name", theme.getName());
                params.put("userId", String.valueOf(user_id));
                params.put("categoryId", String.valueOf(category_id));

                return params;
            }
        };
        requestQueue.add(request);
    }

    public void onChangeThemeFragment(int theme_id, ChangeThemeFragment changeThemeFragment) {
        String url_to_check_user = this.url + "/theme/" + theme_id + "/user";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request_to_check = new StringRequest(
                Request.Method.GET,
                url_to_check_user,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isCorrect = (Constants.USER_ID == jsonObject.getInt("id"));
                        if (isCorrect) {
                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                                    .add(R.id.fl_theme_main, changeThemeFragment)
                                    .commit();
                        } else {
                            Toast.makeText(context, warning, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );

        requestQueue.add(request_to_check);
    }

    @Override
    public void updateTheme(int theme_id, String name) {

        String url = this.url + "/theme/" + theme_id + "/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    getAllThemesByCategoryId();
                    ((AdapterActivity) context).updateAdapter();
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("name", name);

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void deleteTheme(int theme_id) {

        String url_to_check_user = this.url + "/theme/" + theme_id + "/user";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request_to_check = new StringRequest(
                Request.Method.GET,
                url_to_check_user,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isCorrect = (Constants.USER_ID == jsonObject.getInt("id"));
                        if (isCorrect) {
                            onDelete(theme_id);
                        } else {
                            Toast.makeText(context, warning, Toast.LENGTH_SHORT).show();
                            ((AdapterActivity)context).updateAdapter();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );

        requestQueue.add(request_to_check);
    }

    public void deleteFromUserAll(int theme_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url_to_delete = this.url + "/theme/" + theme_id;
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url_to_delete,
                response -> {
                    getAllThemesByUserId();
                },
                errorListener
        );

        requestQueue.add(request);
    }

    public void onDelete(int theme_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url_to_delete = this.url + "/theme/" + theme_id;
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url_to_delete,
                response -> {
                    getAllThemesByCategoryId();
                },
                errorListener
        );

        requestQueue.add(request);
    }

    public void setDrawable(ThemeAdapter.ThemeHolder themeHolder, int theme_id) {

        String url_to_check_user = this.url + "/theme/" + theme_id + "/user";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request_to_check = new StringRequest(
                Request.Method.GET,
                url_to_check_user,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean isCorrect = (Constants.USER_ID == jsonObject.getInt("id"));
                        if (isCorrect) {
                            themeHolder.setImageView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );

        requestQueue.add(request_to_check);
    }
}
