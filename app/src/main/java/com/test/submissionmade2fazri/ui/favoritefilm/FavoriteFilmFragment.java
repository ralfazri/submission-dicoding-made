package com.test.submissionmade2fazri.ui.favoritefilm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.adapter.FavoriteFilmAdapter;
import com.test.submissionmade2fazri.db.FavoriteFilmHelper;
import com.test.submissionmade2fazri.model.Film;
import com.test.submissionmade2fazri.ui.home.HomeViewModel;

import java.util.ArrayList;

public class FavoriteFilmFragment extends Fragment {

    private ArrayList<Film> filmArrayList = new ArrayList<>();
    private FavoriteFilmHelper favoriteFilmHelper;
    private FavoriteFilmAdapter adapter;


    public FavoriteFilmFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteFilmHelper = FavoriteFilmHelper.getInstance(view.getContext());
        favoriteFilmHelper.open();

        RecyclerView rvFavorFilm = view.findViewById(R.id.rv_filmFavorite);
        rvFavorFilm.setHasFixedSize(true);
        rvFavorFilm.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        adapter = new FavoriteFilmAdapter(view.getContext());
        adapter.setFilms(filmArrayList);
        rvFavorFilm.setAdapter(adapter);
    }



    @Override
    public void onResume() {
        super.onResume();
        filmArrayList = favoriteFilmHelper.getAllFilm();
        adapter.setData(filmArrayList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteFilmHelper.close();
    }
}
