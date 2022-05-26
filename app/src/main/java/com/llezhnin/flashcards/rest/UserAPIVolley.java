package com.llezhnin.flashcards.rest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.llezhnin.flashcards.Constants;
import com.llezhnin.flashcards.activities.LauncherActivity;
import com.llezhnin.flashcards.activities.MainActivity;
import com.llezhnin.flashcards.domain.User;
import com.llezhnin.flashcards.fragments.Login;
import com.llezhnin.flashcards.fragments.Registration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAPIVolley implements UserAPI {

    public static final String API_TEST = "API_TEST";
    private final Context context;
    private final String url = Constants.BASE_URL;
    public static int user_id;

    private Response.ErrorListener errorListener = error -> Log.d(API_TEST, error.toString());

    public UserAPIVolley(Context context) {
        this.context = context;
        user_id = Constants.USER_ID;
    }

    @Override
    public void register(User user, Registration registration) {
        String url = this.url + "/user/new/";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    Log.d(API_TEST + "addNewUser ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Constants.USER_ID = jsonObject.getInt("id");
                        Constants.USERNAME = jsonObject.getString("nickname");
                        updateUserId();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((LauncherActivity)context).getSupportFragmentManager().beginTransaction()
                                .remove(((Fragment)registration))
                                .commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(API_TEST + "_USERID", String.valueOf(Constants.USER_ID));
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("nickname", user.getNickname());
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());

                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void login(String email, String password, Login login) {
        String url = this.url + "/user/email/" + email;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Log.d(API_TEST + "GetUserByEmail ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String pass = jsonObject.getString("password");
                        if(pass.equals(password)) {
                            Constants.USER_ID = jsonObject.getInt("id");
                            Constants.USERNAME = jsonObject.getString("nickname");
                            updateUserId();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            ((LauncherActivity)context).getSupportFragmentManager().beginTransaction()
                                    .remove(((Fragment)login))
                                    .commit();
                        } else {
                            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                        Log.d(API_TEST + "_USERID", String.valueOf(Constants.USER_ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                errorListener
        );
        requestQueue.add(request);
    }

    public void updateUserId() {
        user_id = Constants.USER_ID;
    }
}
