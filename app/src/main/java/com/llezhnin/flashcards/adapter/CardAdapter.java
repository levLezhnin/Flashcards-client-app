package com.llezhnin.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llezhnin.flashcards.activities.CardActivity;
import com.llezhnin.flashcards.activities.LearningActivity;
import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.fragments.ChangeCardFragment;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Card> cardList;

    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.cardList = cardList;
    }

    private class CardHolder extends RecyclerView.ViewHolder {

        private TextView key;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            key = itemView.findViewById(R.id.tv_card_key);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.card_item, viewGroup, false);

        return new CardAdapter.CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Card card = NoDB.CARDS.get(i);

        ((CardHolder) viewHolder).key.setText(card.getKey());

        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LearningActivity.class);
            view.getContext().startActivity(intent);
        });
        viewHolder.itemView.setOnLongClickListener(view -> {
            ChangeCardFragment changeCardFragment = new ChangeCardFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Card", card);

            changeCardFragment.setArguments(bundle);

            new ThemeAPIVolley(context).onChangeCardFragment(CardActivity.CURRENT_THEME_ID, ((CardActivity)context), changeCardFragment);

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return NoDB.CARDS.size();
    }

}
