package com.test.submissionmade2fazri.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.test.submissionmade2fazri.db.FavoriteFilmTvContract.FavoriteFilmTvColumns.FAVORITE_FILM_TV_TABLE_NAME;

public class FavoriteFilmTvDatabaseHelper extends SQLiteOpenHelper {

    private static final String FAVORITE_FILM_TV_DATABASE_NAME = "dbfavoritefilmtv";

    private static final int FAVORITE_FILM_TV_DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_FILM_YV = String.format("CREATE TABLE %s" +
            " (%s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA)",
            FAVORITE_FILM_TV_TABLE_NAME,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.ID,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.NAME,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.DATE,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.LANGUANGE,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.VOTE,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.POPULARITY,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.OVERVIEW,
            FavoriteFilmTvContract.FavoriteFilmTvColumns.POSTER_PATH
    );

    FavoriteFilmTvDatabaseHelper(Context context){
        super(context, FAVORITE_FILM_TV_DATABASE_NAME, null, FAVORITE_FILM_TV_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_FILM_YV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_FILM_TV_TABLE_NAME);
        onCreate(db);
    }
}
