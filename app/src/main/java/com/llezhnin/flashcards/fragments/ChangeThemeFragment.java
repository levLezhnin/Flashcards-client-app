package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;
import com.llezhnin.flashcards.rest.UserAPIVolley;

public class ChangeThemeFragment extends Fragment {

    private EditText name;
    private AppCompatButton btn_accept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_theme, container, false);

        Theme theme = (Theme)getArguments().getSerializable("Theme");

        name = view.findViewById(R.id.et_theme_name);
        name.setText(theme.getName());
        btn_accept = view.findViewById(R.id.btn_change_theme);

        btn_accept.setOnClickListener(view1 -> {
            new ThemeAPIVolley(getContext())
                    .updateTheme(theme.getId(),
                            name.getText().toString());
            getFragmentManager().beginTransaction()
                    .remove(ChangeThemeFragment.this)
                    .commit();
        });
        return view;
    }


}