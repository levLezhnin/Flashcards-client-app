package com.llezhnin.flashcards.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.rest.CardAPIVolley;

public class AddCardFragment extends Fragment {

    private EditText et_card_key, et_card_value;
    private AppCompatButton btn_new_card;

    public static int THEME_ID = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        et_card_key = view.findViewById(R.id.et_card_key);
        et_card_value = view.findViewById(R.id.et_card_value);
        btn_new_card = view.findViewById(R.id.btn_new_card);

        btn_new_card.setOnClickListener(view1 -> {
            CardAPIVolley.theme_id = THEME_ID;
            new CardAPIVolley(getContext())
                    .addCardIntoTheme(new Card(et_card_key.getText().toString(),
                                    et_card_value.getText().toString()));
            getFragmentManager().beginTransaction()
                    .remove(AddCardFragment.this)
                    .commit();
        });
        return view;
    }
}