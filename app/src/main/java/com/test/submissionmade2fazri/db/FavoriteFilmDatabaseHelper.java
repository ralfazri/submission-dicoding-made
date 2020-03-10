package com.test.submissionmade2fazri.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.FAVORITE_FILM_TABLE_NAME;

public class FavoriteFilmDatabaseHelper extends SQLiteOpenHelper {

    private static final String FAVORITE_FILM_DATABASE_NAME = "dbfavoritefilm";

    private static final int FAVORITE_FILM_DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_FILM = String.format("CREATE TABLE %s" +
            " (%s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA," +
            " %s DATA)",
            FAVORITE_FILM_TABLE_NAME,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.ID,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.TITLE,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.LANGUANGE,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.VOTE,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.POPULAR,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.DATE,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.DETAIL,
            FavoriteFilmDatabaseContract.FavoriteFilmColumns.IMAGE
    );

    public FavoriteFilmDatabaseHelper(Context context){
        super(context, FAVORITE_FILM_DATABASE_NAME, null, FAVORITE_FILM_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_FILM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_FILM_TABLE_NAME);
        onCreate(db);
    }
}
