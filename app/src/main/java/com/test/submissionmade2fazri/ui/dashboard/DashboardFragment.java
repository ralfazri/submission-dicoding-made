package com.test.submissionmade2fazri.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.submissionmade2fazri.adapter.FilmTVAdapter;
import com.test.submissionmade2fazri.model.FilmTv;
import com.test.submissionmade2fazri.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private ArrayList<FilmTv> filmTvData = new ArrayList<>();
    private FilmTVAdapter filmTVAdapter;
    private ProgressDialog progressDialog;
    private DashboardViewModel dashboardViewModel;

    //fragment buat film_tv
    public DashboardFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));

        if (filmTvData.size() <= 0){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        dashboardViewModel.getFilmTv().observe(this, getFilmTv);
        dashboardViewModel.setFilmTv();


        RecyclerView rvFilmTv = view.findViewById(R.id.rv_filmTv);
        rvFilmTv.setHasFixedSize(true);
        rvFilmTv.setLayoutManager(new GridLayoutManager(getContext(),2));
        filmTVAdapter = new FilmTVAdapter(view.getContext());
        filmTVAdapter.setFilmTvData(filmTvData);
        rvFilmTv.setAdapter(filmTVAdapter);
    }

    private Observer<ArrayList<FilmTv>> getFilmTv = new Observer<ArrayList<FilmTv>>() {
        @Override
        public void onChanged(ArrayList<FilmTv> filmTvs) {
            if (filmTvs !=null){
                filmTvData = filmTvs;
                filmTVAdapter.setFilmTvData(filmTvData);
            }
            progressDialog.dismiss();
        }
    };



}