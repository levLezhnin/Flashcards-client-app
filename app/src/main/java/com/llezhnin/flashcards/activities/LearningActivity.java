package com.llezhnin.flashcards.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.llezhnin.flashcards.OnSwipeTouchListener;
import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.fragments.CardKeyFragment;
import com.llezhnin.flashcards.fragments.CardValueFragment;
import com.llezhnin.flashcards.nodb.NoDB;

import java.text.MessageFormat;
import java.util.Collections;

public class LearningActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TextView tv_progress;
    private boolean isKey;
    private int CURRENT_CARD = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        frameLayout = findViewById(R.id.layout_to_place_cards);
        tv_progress = findViewById(R.id.text);

        Collections.shuffle(NoDB.CARDS);

        CardKeyFragment.CURRENT_CARD = CURRENT_CARD;
        CardValueFragment.CURRENT_CARD = CURRENT_CARD;
        setTVProgress(CURRENT_CARD);
        CardKeyFragment startKeyFragment = new CardKeyFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.layout_to_place_cards, startKeyFragment)
                .commit();

        frameLayout.setOnTouchListener(new OnSwipeTouchListener(LearningActivity.this) {
            public void onSwipeLeft() {
                CURRENT_CARD++;
                if(CURRENT_CARD >= NoDB.CARDS.size()) {
                    CURRENT_CARD = 0;
                    Collections.shuffle(NoDB.CARDS);
                }
                CardKeyFragment.CURRENT_CARD = CURRENT_CARD;
                CardValueFragment.CURRENT_CARD = CURRENT_CARD;
                CardKeyFragment cardKeyFragment1 = new CardKeyFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.layout_to_place_cards, cardKeyFragment1)
                        .commit();
                setTVProgress(CURRENT_CARD);
            }

            public void onSwipeRight() {
                CURRENT_CARD--;
                if(CURRENT_CARD < 0) {
                    CURRENT_CARD = NoDB.CARDS.size()-1;
                    Collections.shuffle(NoDB.CARDS);
                }
                CardKeyFragment.CURRENT_CARD = CURRENT_CARD;
                CardValueFragment.CURRENT_CARD = CURRENT_CARD;
                CardKeyFragment cardKeyFragment1 = new CardKeyFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.layout_to_place_cards, cardKeyFragment1)
                        .commit();
                setTVProgress(CURRENT_CARD);
            }

            public void onTap() {
                if(isKey) {
                    CardValueFragment cardValueFragment = new CardValueFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.layout_to_place_cards, cardValueFragment)
                            .commit();
                } else {
                    CardKeyFragment cardKeyFragment = new CardKeyFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.layout_to_place_cards, cardKeyFragment)
                            .commit();
                }

                isKey = !isKey;
            }
        });

        isKey = true;
    }

    public void setTVProgress(int current_card) {
        tv_progress.setText(MessageFormat.format("{0}/{1}", current_card+1, NoDB.CARDS.size()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CURRENT_CARD = 0;
    }
}