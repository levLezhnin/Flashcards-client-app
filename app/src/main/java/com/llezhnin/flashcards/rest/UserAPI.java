package com.llezhnin.flashcards.rest;

import android.content.Intent;

import com.llezhnin.flashcards.activities.LauncherActivity;
import com.llezhnin.flashcards.domain.User;
import com.llezhnin.flashcards.fragments.Login;
import com.llezhnin.flashcards.fragments.Registration;

public interface UserAPI {

    void register(User user, Registration registration);

    void login(String email, String password, Login login);
}
