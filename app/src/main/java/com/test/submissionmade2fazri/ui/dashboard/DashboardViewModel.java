package com.test.submissionmade2fazri.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.model.FilmTv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DashboardViewModel extends ViewModel {
    private static final String API_FILM_TV = Constant.API_FILM_YV_URL+Constant.API_KEY+Constant.API_LANGUAGE;
    private MutableLiveData<ArrayList<FilmTv>> listFilmTv = new MutableLiveData<>();

    public void setFilmTv(){
        AsyncHttpClient http = new AsyncHttpClient();
        final ArrayList<FilmTv> listDataFilmTv = new ArrayList<>();

        String url = API_FILM_TV;

        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject data = new JSONObject(result);
                    JSONArray list = data.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject dataFilmTv = list.getJSONObject(i);
                        FilmTv tvTtem = new FilmTv(dataFilmTv);
                        listDataFilmTv.add(tvTtem);
                    }
                    listFilmTv.postValue(listDataFilmTv);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

    }

    public LiveData<ArrayList<FilmTv>> getFilmTv() {
        return listFilmTv;
    }
}