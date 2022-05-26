package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.nodb.NoDB;

public class CardValueFragment extends Fragment {

    private TextView card_value;
    public static int CURRENT_CARD = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_value, container, false);
        card_value = view.findViewById(R.id.card_value);
        String value = NoDB.CARDS.get(CURRENT_CARD).getValue();
        card_value.setText(value);
        return view;
    }
}