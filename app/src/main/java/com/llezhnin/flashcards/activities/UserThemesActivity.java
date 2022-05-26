package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.adapter.ThemeAdapter;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

public class UserThemesActivity extends AppCompatActivity implements AdapterActivity {

    private RecyclerView rv_user_themes;
    private ThemeAdapter themeAdapter;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_themes);

        new ThemeAPIVolley(this).getAllThemesByUserId();

        rv_user_themes = findViewById(R.id.rv_user_themes);
        themeAdapter = new ThemeAdapter(this, NoDB.THEMES);
        rv_user_themes.setAdapter(themeAdapter);

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
                int position = viewHolder.getAdapterPosition();
                Theme theme = NoDB.THEMES.get(position);
                if(i == ItemTouchHelper.LEFT) {
                    new ThemeAPIVolley(UserThemesActivity.this)
                            .deleteFromUserAll(theme.getId());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);;
        itemTouchHelper.attachToRecyclerView(rv_user_themes);
    }

    @Override
    public void updateAdapter() {
        themeAdapter.notifyDataSetChanged();
    }
}