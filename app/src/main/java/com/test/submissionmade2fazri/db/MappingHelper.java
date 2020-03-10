package com.test.submissionmade2fazri.db;

import android.database.Cursor;

import com.test.submissionmade2fazri.model.Film;

import java.util.ArrayList;

public class MappingHelper {
    public  static ArrayList<Film> mapCursorFilm(Cursor filmCursor){
        ArrayList<Film> filmArrayList = new ArrayList<>();

        while (filmCursor.moveToNext()){
            int ID = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.ID));
            String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.TITLE));
            String language =  filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.LANGUANGE));
            String vote = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.VOTE));
            Double popularity = filmCursor.getDouble(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.POPULAR));
            String date = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.DATE));
            String overview = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.DETAIL));
            String photo =  filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteFilmDatabaseContract.FavoriteFilmColumns.IMAGE));

            Film film = new Film();
            film.setFilmId(ID);
            film.setTitle(title);
            film.setLanguage(language);
            film.setVote(vote);
            film.setPopularity(popularity);
            film.setDate(date);
            film.setOverview(overview);
            film.setPhoto(photo);
            filmArrayList.add(film);
        }
        return filmArrayList;
    }
}
