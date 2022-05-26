package com.llezhnin.flashcards.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.activities.MainActivity;
import com.llezhnin.flashcards.domain.User;
import com.llezhnin.flashcards.rest.UserAPIVolley;

public class Registration extends Fragment {

    private EditText nickname, email, password;
    private AppCompatButton btn_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        nickname = view.findViewById(R.id.et_user_nickname);
        email = view.findViewById(R.id.et_user_email);
        password = view.findViewById(R.id.et_user_password);
        btn_new = view.findViewById(R.id.btn_sign_up);

        btn_new.setOnClickListener(view1 -> {
            if(!nickname.getText().toString().equals("")
            && !email.getText().toString().equals("")
                    && !password.getText().toString().equals("")) {
                new UserAPIVolley(getContext()).register(
                        new User(nickname.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString()),
                        Registration.this
                );
            } else {
                Toast.makeText(getContext(), "Fill all the fields!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}