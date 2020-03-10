package com.test.submissionmade2fazri.ui.favoritetv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.adapter.FavoriteFilmTvAdapter;
import com.test.submissionmade2fazri.db.FavoriteFilmTvHelper;
import com.test.submissionmade2fazri.model.FilmTv;

import java.util.ArrayList;

public class FavoriteTvFragment extends Fragment {

    private ArrayList<FilmTv> filmTvArrayList = new ArrayList<>();
    private FavoriteFilmTvHelper favoriteFilmTvHelper;
    private FavoriteFilmTvAdapter favoriteFilmTvAdapter;

    public FavoriteTvFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteFilmTvHelper = FavoriteFilmTvHelper.getInstance(view.getContext());
        favoriteFilmTvHelper.open();

        RecyclerView rcFilmTv = view.findViewById(R.id.rv_filmTvFavorite);
        rcFilmTv.setHasFixedSize(true);
        rcFilmTv.setLayoutManager(new GridLayoutManager(view.getContext(),2));

        favoriteFilmTvAdapter = new FavoriteFilmTvAdapter(view.getContext());
        favoriteFilmTvAdapter.setFilmTvs(filmTvArrayList);
        rcFilmTv.setAdapter(favoriteFilmTvAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        filmTvArrayList = favoriteFilmTvHelper.getAllFilmTv();
        favoriteFilmTvAdapter.setData(filmTvArrayList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteFilmTvHelper.close();
    }
}
