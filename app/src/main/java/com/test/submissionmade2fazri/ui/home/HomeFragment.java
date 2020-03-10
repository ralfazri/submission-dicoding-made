package com.test.submissionmade2fazri.ui.home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.test.submissionmade2fazri.model.Film;
import com.test.submissionmade2fazri.adapter.FilmAdapter;
import com.test.submissionmade2fazri.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private FilmAdapter filmAdapter;
    private ArrayList<Film> filmData = new ArrayList<>();
    private ProgressDialog progressDialog;
    private HomeViewModel homeViewModel;
    private Menu menu;
    private MenuInflater inflater;
    private TabLayout tabs;


    public HomeFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));

        if (filmData.size() <= 0){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getFilm().observe(this, getFilm);
        homeViewModel.setFilm();

        RecyclerView recyclerView = view.findViewById(R.id.rv_film);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        filmAdapter = new FilmAdapter(view.getContext());
        filmAdapter.setDataFilm(filmData);
        recyclerView.setAdapter(filmAdapter);


    }

    private Observer<ArrayList<Film>> getFilm = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null){
                filmAdapter.setDataFilm(films);
            }

            progressDialog.dismiss();
        }
    };


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}