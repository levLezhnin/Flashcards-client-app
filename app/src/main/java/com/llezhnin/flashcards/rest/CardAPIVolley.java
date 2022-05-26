package com.llezhnin.flashcards.rest;

import android.content.Context;
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
import com.llezhnin.flashcards.activities.CardActivity;
import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.domain.mapper.CardMapper;
import com.llezhnin.flashcards.nodb.NoDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardAPIVolley implements CardAPI {

    public static final String API_TEST = "API_TEST";
    private final Context context;
    private final String url = Constants.BASE_URL;
    public static int user_id;
    public static int theme_id;

    private Response.ErrorListener errorListener = error -> Log.d(API_TEST, error.toString());

    public CardAPIVolley(Context context) {
        this.context = context;
        user_id = Constants.USER_ID;
    }

    @Override
    public void addCardIntoTheme(Card card) {
        String url = this.url + "/card/new/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.d(API_TEST + "addNewCard ", response);
                    getAllCardsByThemeId();
                    ((AdapterActivity)context).updateAdapter();
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("key", card.getKey());
                params.put("value", card.getValue());
                params.put("themeId", String.valueOf(theme_id));

                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void updateCard(int card_id, String key, String value) {
        String url = this.url + "/card/"+card_id+"/";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.d(API_TEST + "updateCard ", response);
                    getAllCardsByThemeId();
                    ((AdapterActivity)context).updateAdapter();
                    },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("key", key);
                params.put("value", value);

                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void getAllCardsByThemeId() {
        String url = this.url + "/theme/"+theme_id+"/cards";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        NoDB.CARDS.clear();
                        try {
                            for(int i = 0; i < response.length(); i++) {
                                JSONObject card = response.getJSONObject(i);
                                NoDB.CARDS.add(CardMapper.fromJson(card));
                            }
                            Log.d(API_TEST + "getCardsByTheme", String.valueOf(NoDB.CARDS));
                            System.out.println(NoDB.CARDS);
                            ((AdapterActivity)context).updateAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );
        requestQueue.add(request);
    }

    public void delete(int theme_id, int card_id) {
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
                            deleteCard(card_id);
                        } else {
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

    @Override
    public void deleteCard(int card_id) {
        String url = this.url + "/card/"+ card_id;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                response -> {
                    Log.d(API_TEST + "deleteCard ", response);
                    ((AdapterActivity)context).updateAdapter();
                },
                errorListener
        );
        requestQueue.add(request);
    }
}
