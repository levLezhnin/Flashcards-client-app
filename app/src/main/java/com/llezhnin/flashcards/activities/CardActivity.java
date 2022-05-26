package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.adapter.CardAdapter;
import com.llezhnin.flashcards.domain.Card;
import com.llezhnin.flashcards.fragments.AddCardFragment;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.CardAPIVolley;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

public class CardActivity extends AppCompatActivity implements AdapterActivity {

    private RecyclerView rvCard;
    private CardAdapter cardAdapter;

    private AppCompatButton btnNew;

    private ItemTouchHelper.SimpleCallback simpleCallback;

    public static int CURRENT_THEME_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        btnNew = findViewById(R.id.btn_add);

        btnNew.setOnClickListener(view -> {

            AddCardFragment.THEME_ID = CURRENT_THEME_ID;

            AddCardFragment addCardFragment = new AddCardFragment();

            new ThemeAPIVolley(this).onAddCardFragment(CURRENT_THEME_ID, this, addCardFragment);

        });

        rvCard = findViewById(R.id.rv_card);
        cardAdapter = new CardAdapter(this, NoDB.CARDS);
        rvCard.setAdapter(cardAdapter);

        simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Card card = NoDB.CARDS.get(viewHolder.getAdapterPosition());
                if (i == ItemTouchHelper.LEFT) {
                    new CardAPIVolley(CardActivity.this)
                            .delete(CURRENT_THEME_ID, card.getId());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvCard);

        CardAPIVolley.theme_id = CURRENT_THEME_ID;
        new CardAPIVolley(this).getAllCardsByThemeId();

    }

    public void updateAdapter() {
        cardAdapter.notifyDataSetChanged();
    }
}