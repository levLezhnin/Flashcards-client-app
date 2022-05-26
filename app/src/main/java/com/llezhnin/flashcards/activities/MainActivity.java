package com.llezhnin.flashcards.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.llezhnin.flashcards.Constants;
import com.llezhnin.flashcards.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Button btn_to_find_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2965AB")));
        drawerToggle.syncState();

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(menuItem -> {

            int id = menuItem.getItemId();

            if (id == R.id.favourite_categories) {
                if(Constants.USER_ID != 0) {
                    Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Sign In or Login first!", Toast.LENGTH_SHORT).show();
                }
            }
            if(id == R.id.created_themes) {
                if(Constants.USER_ID != 0) {
                    Intent intent = new Intent(getApplicationContext(), UserThemesActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Sign In or Login first!", Toast.LENGTH_SHORT).show();
                }
            }
            if(id == R.id.quit) {
                Constants.USER_ID = 0;
                Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        });

        nav_view.getMenu().getItem(0).setTitle(Constants.USERNAME);

        btn_to_find_categories = findViewById(R.id.btn_to_find_categories);

        btn_to_find_categories.setOnClickListener(view -> {
            if(Constants.USER_ID != 0) {
                Intent intent = new Intent(getApplicationContext(), FindCategoriesActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Sign In or Login first!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}