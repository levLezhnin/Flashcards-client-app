package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.adapter.ThemeAdapter;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.fragments.AddThemeFragment;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

public class ThemeActivity extends AppCompatActivity implements AdapterActivity {

    private RecyclerView rvTheme;
    private ThemeAdapter themeAdapter;

    private AppCompatButton btnNew;

    private ItemTouchHelper.SimpleCallback simpleCallback;

    public static int CURRENT_CATEGORY_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        rvTheme = findViewById(R.id.rv_theme);
        btnNew = findViewById(R.id.btn_add);
        themeAdapter = new ThemeAdapter(ThemeActivity.this, NoDB.THEMES);
        rvTheme.setAdapter(themeAdapter);
        ThemeAPIVolley.category_id = CURRENT_CATEGORY_ID;
        new ThemeAPIVolley(ThemeActivity.this).getAllThemesByCategoryId();

        btnNew.setOnClickListener(view -> {
            AddThemeFragment.CATEGORY_ID = CURRENT_CATEGORY_ID;
            AddThemeFragment addThemeFragment = new AddThemeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_theme_main, addThemeFragment)
                    .commit();
        });

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
                if (i == ItemTouchHelper.LEFT) {
                    new ThemeAPIVolley(ThemeActivity.this)
                            .deleteTheme(theme.getId());
                }

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvTheme);
    }

    @Override
    public void updateAdapter() {
        themeAdapter.notifyDataSetChanged();
    }
}