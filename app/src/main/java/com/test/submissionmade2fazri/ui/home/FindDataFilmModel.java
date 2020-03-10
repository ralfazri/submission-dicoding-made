package com.test.submissionmade2fazri.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.model.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FindDataFilmModel extends ViewModel {

    private static final String API_FILM_FIND = Constant.API_KEY_SEARCH_MOVIE + Constant.API_KEY + Constant.LANG_SEARCH;
    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();

    public void setFindFilmData(String query){
        AsyncHttpClient http = new AsyncHttpClient();
        final ArrayList<Film> listDataFilm = new ArrayList<>();
        String url = API_FILM_FIND + query;
        http.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject data = new JSONObject(result);
                    JSONArray list = data.getJSONArray("results");
                    String value = data.getString("total_results");

                    if (value!=null) {
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject filmItem = list.getJSONObject(i);
                            Film filmData = new Film(filmItem);
                            listDataFilm.add(filmData);
                        }
                        listFilm.postValue(listDataFilm);
                    }

                } catch (JSONException error) {
                    Log.d("Exception", error.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Film>> getFindFilm(){
        return listFilm;
    }
}
