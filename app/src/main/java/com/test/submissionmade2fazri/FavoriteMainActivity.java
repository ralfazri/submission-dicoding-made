package com.test.submissionmade2fazri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.test.submissionmade2fazri.ui.favoritefilm.FavoriteFilmFragment;
import com.test.submissionmade2fazri.ui.favoritetv.FavoriteTvFragment;

public class FavoriteMainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new FavoriteFilmFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
            case R.id.navigation_dashboard:
                fragment = new FavoriteTvFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
        }

        return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_main);
        BottomNavigationView btmView = findViewById(R.id.nav_view);
        btmView.setOnNavigationItemSelectedListener(navItem);
        getSupportActionBar().hide();

        if (savedInstanceState == null){
            btmView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
