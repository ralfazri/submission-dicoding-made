package com.test.submissionmade2fazri;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.test.submissionmade2fazri.activity.FilmSearchActivity;
import com.test.submissionmade2fazri.activity.FilmTvSearchActivity;
import com.test.submissionmade2fazri.activity.SettingActivity;
import com.test.submissionmade2fazri.ui.dashboard.DashboardFragment;
import com.test.submissionmade2fazri.ui.dashboard.DashboardViewModel;
import com.test.submissionmade2fazri.ui.favoritetv.FavoriteTvFragment;
import com.test.submissionmade2fazri.ui.home.HomeFragment;
import com.test.submissionmade2fazri.ui.home.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;
    private DashboardViewModel dashboardViewModel;
    private ViewGroup viewGroup;



    private BottomNavigationView.OnNavigationItemSelectedListener navigationItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
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
        setContentView(R.layout.activity_main);
        BottomNavigationView btmView = findViewById(R.id.nav_view);
        btmView.setOnNavigationItemSelectedListener(navigationItem);



        if (savedInstanceState == null){
            btmView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.action_change_settings){
            startActivity(new Intent(this, SettingActivity.class));
        }else if (item.getItemId() == R.id.btnLikeMain){
            startActivity(new Intent(this, FavoriteMainActivity.class));
        }else if (item.getItemId() == R.id.ic_search){
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.item_seacrh_dialog);
            dialog.setTitle("Pilih Aksi");
            dialog.show();

            ImageView imgFilm = dialog.findViewById(R.id.iv_film_search);
            ImageView imgFilmTv = dialog.findViewById(R.id.iv_film_tv_search);

            imgFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, FilmSearchActivity.class));
                }
            });

            imgFilmTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, FilmTvSearchActivity.class));
                }
            });

            Toast.makeText(this, R.string.select_string, Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }
}
