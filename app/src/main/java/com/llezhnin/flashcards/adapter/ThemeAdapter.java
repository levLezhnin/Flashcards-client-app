package com.llezhnin.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.activities.CardActivity;
import com.llezhnin.flashcards.activities.ThemeActivity;
import com.llezhnin.flashcards.domain.Theme;
import com.llezhnin.flashcards.fragments.ChangeThemeFragment;
import com.llezhnin.flashcards.nodb.NoDB;
import com.llezhnin.flashcards.rest.ThemeAPIVolley;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<Theme> themeList;

    public ThemeAdapter(Context context, List<Theme> themeList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.themeList = themeList;
    }

    public class ThemeHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView imageView;

        public void setImageView() {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.is_user_a_author));
        }

        public ThemeHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_theme_name);
            imageView = itemView.findViewById(R.id.user_is_theme_author);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.theme_item, viewGroup, false);

        return new ThemeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Theme theme = NoDB.THEMES.get(i);

        ((ThemeHolder) viewHolder).name.setText(theme.getName());
        new ThemeAPIVolley(context).setDrawable((ThemeHolder) viewHolder, theme.getId());

        viewHolder.itemView.setOnClickListener(view -> {
            Theme theme_tapped = NoDB.THEMES.get(viewHolder.getAdapterPosition());
            Intent intent = new Intent(view.getContext(), CardActivity.class);
            CardActivity.CURRENT_THEME_ID = theme_tapped.getId();
            view.getContext().startActivity(intent);
        });

        viewHolder.itemView.setOnLongClickListener(view -> {

                ChangeThemeFragment changeThemeFragment = new ChangeThemeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Theme", theme);

                changeThemeFragment.setArguments(bundle);

                new ThemeAPIVolley(view.getContext()).onChangeThemeFragment(theme.getId(), changeThemeFragment);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return NoDB.THEMES.size();
    }
}
