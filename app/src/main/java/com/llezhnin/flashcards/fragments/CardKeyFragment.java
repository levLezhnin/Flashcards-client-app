package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.nodb.NoDB;

public class CardKeyFragment extends Fragment {

    private TextView card_key;
    public static int CURRENT_CARD = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_key, container, false);
        card_key = view.findViewById(R.id.card_key);
        card_key.setText(NoDB.CARDS.get(CURRENT_CARD).getKey());
        return view;
    }
}