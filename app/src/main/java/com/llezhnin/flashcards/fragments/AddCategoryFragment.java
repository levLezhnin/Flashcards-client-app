package com.llezhnin.flashcards.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.llezhnin.flashcards.R;
import com.llezhnin.flashcards.domain.Category;
import com.llezhnin.flashcards.rest.CategoryAPIVolley;

public class AddCategoryFragment extends Fragment {

    private EditText category_name;
    private AppCompatButton btn_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        category_name = view.findViewById(R.id.et_category_name);
        btn_new = view.findViewById(R.id.btn_new_category);

        btn_new.setOnClickListener(view1 -> {
            new CategoryAPIVolley(getContext()).addNewCategory(
                    new Category(category_name.getText().toString())
            );
            getFragmentManager().beginTransaction()
                    .remove(AddCategoryFragment.this)
                    .commit();
        });
        return view;
    }
}