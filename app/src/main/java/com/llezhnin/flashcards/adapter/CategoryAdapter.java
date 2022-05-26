package com.llezhnin.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.activities.ThemeActivity;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.nodb.NoDB;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.categoryList = categoryList;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView name;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_category_name);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.category_item, viewGroup, false);

        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Category category = NoDB.CATEGORIES.get(i);

        ((CategoryHolder) viewHolder).name.setText(category.getName());

        viewHolder.itemView.setOnClickListener(view -> {
            Category category_tapped = NoDB.CATEGORIES.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(view.getContext(), ThemeActivity.class);
            ThemeActivity.CURRENT_CATEGORY_ID = category_tapped.getId();
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return NoDB.CATEGORIES.size();
    }
}
