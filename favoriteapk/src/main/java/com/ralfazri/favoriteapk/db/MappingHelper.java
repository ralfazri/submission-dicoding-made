package com.ralfazri.favoriteapk.db;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelper {

    public  static ArrayList<Film> mapCursorFilm(Cursor filmCursor){
        ArrayList<Film> filmArrayList = new ArrayList<>();

        while (filmCursor.moveToNext()){
            int ID = filmCursor.getInt(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.ID));
            String title = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.TITLE));
            String language =  filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.LANGUANGE));
            String vote = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.VOTE));
            Double popularity = filmCursor.getDouble(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.POPULAR));
            String date = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.DATE));
            String overview = filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.DETAIL));
            String photo =  filmCursor.getString(filmCursor.getColumnIndexOrThrow(FavoriteDatabase.FavoriteFilmColumns.IMAGE));

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
