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
import com.llezhnin.flashcards.activities.LauncherActivity;
import com.llezhnin.flashcards.activities.MainActivity;
import com.llezhnin.flashcards.rest.UserAPIVolley;

public class Login extends Fragment {

    private EditText email, password;
    private AppCompatButton btn_sign_in;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.et_user_email);
        password = view.findViewById(R.id.et_user_password);
        btn_sign_in = view.findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                new UserAPIVolley(getContext()).login(
                        email.getText().toString(),
                        password.getText().toString(),
                        Login.this
                );
            }
        });
        return view;
    }
}