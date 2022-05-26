package com.llezhnin.flashcards.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.adapter.CategoryAdapter;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.CategoryAPIVolley;

public class FindCategoriesActivity extends AppCompatActivity implements AdapterActivity{

    private RecyclerView rv_categories;
    private CategoryAdapter categoryAdapter;
    private ItemTouchHelper.SimpleCallback simpleCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_categories);

        new CategoryAPIVolley(this).getAllCategories();
        rv_categories = findViewById(R.id.rv_categories);
        categoryAdapter = new CategoryAdapter(this, NoDB.CATEGORIES);
        rv_categories.setAdapter(categoryAdapter);

        simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Category category = NoDB.CATEGORIES.get(position);
                if(i == ItemTouchHelper.RIGHT) {
                    new CategoryAPIVolley(
                            FindCategoriesActivity.this
                    ).addCategoryToFavourites(category.getId());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);;
        itemTouchHelper.attachToRecyclerView(rv_categories);
    }

    public void updateAdapter() {
        categoryAdapter.notifyDataSetChanged();
    }
}