package com.test.submissionmade2fazri.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.adapter.SearchFilmAdapter;
import com.test.submissionmade2fazri.model.Film;
import com.test.submissionmade2fazri.ui.home.FindDataFilmModel;
import com.test.submissionmade2fazri.ui.home.HomeViewModel;
import java.util.ArrayList;



public class FilmSearchActivity extends AppCompatActivity {

    private ArrayList<Film> filmData = new ArrayList<>();
    private SearchFilmAdapter searchFilmAdapter = new SearchFilmAdapter(this);
    private SearchView searchFindData;
    private HomeViewModel homeViewModel;
    private FindDataFilmModel findDataFilmModel;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_search);

        homeViewModel  = ViewModelProviders.of(this).get(HomeViewModel.class);
        findDataFilmModel = ViewModelProviders.of(this).get(FindDataFilmModel.class);
        homeViewModel.getFilm().observe(this, getFilm);
        findDataFilmModel.getFindFilm().observe(this, getFilm);

        recyclerView = findViewById(R.id.rv_search_film);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        searchFilmAdapter.setFilm(filmData);
        recyclerView.setAdapter(searchFilmAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem findFilmData = menu.findItem(R.id.ic_search_data);
        searchFindData= (SearchView) findFilmData.getActionView();
        searchFindData.onActionViewExpanded();
        searchFindData.setQueryHint(getResources().getString(R.string.search_find_film));

        searchFindData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findDataFilmModel.setFindFilmData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private Observer<ArrayList<Film>> getFilm = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> filmsData1) {
            if (filmsData1 !=null){
                filmData = filmsData1;
                searchFilmAdapter.setData(filmsData1);
            }
        }
    };
}
