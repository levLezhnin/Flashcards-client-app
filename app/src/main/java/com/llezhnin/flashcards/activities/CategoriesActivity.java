package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.adapter.CategoryAdapter;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.fragments.AddCategoryFragment;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.CategoryAPIVolley;
import com.llezhnin.flashcards.rest.FlashcardsAPIVolley;

public class CategoriesActivity extends AppCompatActivity implements AdapterActivity{

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;

    private AppCompatButton btnAdd;

    private ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        new FlashcardsAPIVolley(this).fill();

        btnAdd = findViewById(R.id.btn_new_category);

        btnAdd.setOnClickListener(view -> {

            AddCategoryFragment addCategoryFragment = new AddCategoryFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_category_main, addCategoryFragment)
                    .commit();

        });

        rvCategory = findViewById(R.id.rv_category);
        categoryAdapter = new CategoryAdapter(this, NoDB.CATEGORIES);
        rvCategory.setAdapter(categoryAdapter);

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
                Category category = NoDB.CATEGORIES.get(position);
                if (i == ItemTouchHelper.LEFT) {
                    new CategoryAPIVolley(CategoriesActivity.this)
                            .removeCategoryFromFavourites(category.getId());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvCategory);
    }

    public void updateAdapter() {
        categoryAdapter.notifyDataSetChanged();
    }
}