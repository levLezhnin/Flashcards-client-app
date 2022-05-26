package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

public class AddThemeFragment extends Fragment {

    private EditText et_theme_name;
    private AppCompatButton btn_new_theme;

    public static int CATEGORY_ID = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_theme, container, false);
        et_theme_name = view.findViewById(R.id.et_theme_name);
        btn_new_theme = view.findViewById(R.id.btn_new_theme);

        btn_new_theme.setOnClickListener(view1 -> {
            ThemeAPIVolley.category_id = CATEGORY_ID;
            new ThemeAPIVolley(getContext())
                    .addNewThemeIntoCategory(new Theme(et_theme_name.getText().toString()));
            getFragmentManager().beginTransaction()
                    .remove(AddThemeFragment.this)
                    .commit();
        });
        return view;
    }
}