package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.activities.CardActivity;
import com.llezhnin.flashcards.activities.ThemeActivity;
import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.rest.CardAPIVolley;

public class ChangeCardFragment extends Fragment {

    private EditText et_card_key, et_card_value;
    private AppCompatButton btn_change_card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_card, container, false);

        Card card = (Card)getArguments().getSerializable("Card");

        et_card_key = view.findViewById(R.id.et_card_key);
        et_card_key.setText(card.getKey());
        et_card_value = view.findViewById(R.id.et_card_value);
        et_card_value.setText(card.getValue());
        btn_change_card = view.findViewById(R.id.btn_change_card);

        btn_change_card.setOnClickListener(view1 -> {
            new CardAPIVolley(getContext())
                    .updateCard(card.getId(),
                            et_card_key.getText().toString(),
                            et_card_value.getText().toString());
            getFragmentManager().beginTransaction()
                    .remove(ChangeCardFragment.this)
                    .commit();
        });

        return view;
    }
}