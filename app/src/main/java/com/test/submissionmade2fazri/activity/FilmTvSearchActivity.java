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
import com.test.submissionmade2fazri.adapter.SearchFilmTvAdapter;
import com.test.submissionmade2fazri.model.FilmTv;
import com.test.submissionmade2fazri.ui.dashboard.DashboardViewModel;
import com.test.submissionmade2fazri.ui.dashboard.FindDataFilmTvModel;

import java.util.ArrayList;

public class FilmTvSearchActivity extends AppCompatActivity {

    private ArrayList<FilmTv> filmTvData = new ArrayList<>();
    private SearchFilmTvAdapter searchFilmTvAdapter;
    private SearchView searchFindData;
    private DashboardViewModel dashboardViewModel ;
    private FindDataFilmTvModel findDataFilmTvModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_tv_search);

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        findDataFilmTvModel = ViewModelProviders.of(this).get(FindDataFilmTvModel.class);
        dashboardViewModel.getFilmTv().observe(this, getFilmTv);
        findDataFilmTvModel.getFindFilmTv().observe(this, getFilmTv);


        recyclerView = findViewById(R.id.rv_search_film_tv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        searchFilmTvAdapter = new SearchFilmTvAdapter(this);
        searchFilmTvAdapter.setFilm(filmTvData);
        recyclerView.setAdapter(searchFilmTvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem findFilmData = menu.findItem(R.id.ic_search_data);
        searchFindData= (SearchView) findFilmData.getActionView();
        searchFindData.onActionViewExpanded();
        searchFindData.setQueryHint(getResources().getString(R.string.search_find_film_tv));

        searchFindData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findDataFilmTvModel.setFindFilmTvData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private Observer<ArrayList<FilmTv>> getFilmTv = new Observer<ArrayList<FilmTv>>() {
        @Override
        public void onChanged(ArrayList<FilmTv> filmtvData1) {
            if (filmtvData1 !=null){
                filmTvData = filmtvData1;
                searchFilmTvAdapter.setData(filmtvData1);
            }
        }
    };
}
