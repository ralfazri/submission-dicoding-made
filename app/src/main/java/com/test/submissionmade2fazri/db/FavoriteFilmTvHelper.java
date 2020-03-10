package com.test.submissionmade2fazri.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.submissionmade2fazri.model.FilmTv;

import java.util.ArrayList;

import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.DATE;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.FAVORITE_FILM_TV_TABLE_NAME;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.ID;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.LANGUANGE;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.NAME;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.OVERVIEW;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.POPULARITY;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.POSTER_PATH;
import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.VOTE;

public class FavoriteFilmTvHelper {
    private static final String DATABASE_TABLE = FAVORITE_FILM_TV_TABLE_NAME;
    private static FavoriteFilmTvDatabaseHelper favoriteFilmTvDatabaseHelper;
    private static FavoriteFilmTvHelper HELPER;
    private static SQLiteDatabase database;

    private FavoriteFilmTvHelper(Context context){
        favoriteFilmTvDatabaseHelper = new FavoriteFilmTvDatabaseHelper(context);
    }

    public static FavoriteFilmTvHelper getInstance(Context context){
        if (HELPER == null){
            synchronized (SQLiteOpenHelper.class){
                if (HELPER ==  null){
                    HELPER = new FavoriteFilmTvHelper(context);
                }
            }
        }
        return HELPER;
    }

    public void open() throws SQLException{
        database = favoriteFilmTvDatabaseHelper.getWritableDatabase();
    }

    public void close(){
        favoriteFilmTvDatabaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<FilmTv> getAllFilmTv(){
        ArrayList<FilmTv> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        FilmTv filmTv;
        if (cursor.getCount() > 0){
            do {
                filmTv = new FilmTv();
                filmTv.setTvId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                filmTv.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                filmTv.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                filmTv.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUANGE)));
                filmTv.setVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                filmTv.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULARITY)));
                filmTv.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                filmTv.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                arrayList.add(filmTv);
                cursor.moveToNext();

            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Long insertFilmTv(FilmTv filmTv){
        ContentValues args = new ContentValues();
        args.put(ID, filmTv.getTvId());
        args.put(NAME, filmTv.getName());
        args.put(DATE, filmTv.getDate());
        args.put(LANGUANGE, filmTv.getLanguage());
        args.put(VOTE, filmTv.getVote());
        args.put(POPULARITY, filmTv.getPopularity());
        args.put(OVERVIEW, filmTv.getOverview());
        args.put(POSTER_PATH, filmTv.getPoster());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFilmTv(int id){
        return database.delete(FAVORITE_FILM_TV_TABLE_NAME, ID + " = '" + id + "'",null);
    }
}
