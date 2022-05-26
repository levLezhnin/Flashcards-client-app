package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.fragments.Login;
import com.llezhnin.flashcards.fragments.Registration;

public class LauncherActivity extends AppCompatActivity {

    private Button btnToRegister, btnToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        btnToRegister = findViewById(R.id.btn_to_register);
        btnToSignIn = findViewById(R.id.btn_to_sign_in);

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registration registration = new Registration();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_main, registration)
                        .commit();
            }
        });

        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login login = new Login();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fl_main, login)
                        .commit();
            }
        });
    }
}