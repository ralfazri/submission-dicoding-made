package com.test.submissionmade2fazri.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.submissionmade2fazri.model.Film;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.DATE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.DETAIL;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.FAVORITE_FILM_TABLE_NAME;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.ID;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.IMAGE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.LANGUANGE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.POPULAR;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.TITLE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.VOTE;

public class FavoriteFilmHelper {

    private static final String DATABASE_TABLE = FAVORITE_FILM_TABLE_NAME;
    private static FavoriteFilmDatabaseHelper favoriteFilmDatabaseHelper;
    private static FavoriteFilmHelper HELPER;
    private static SQLiteDatabase database;

    public FavoriteFilmHelper(Context context){
        favoriteFilmDatabaseHelper = new FavoriteFilmDatabaseHelper(context);
    }

    public static FavoriteFilmHelper getInstance(Context context){
        if (HELPER == null){
            synchronized (SQLiteOpenHelper.class){
                if (HELPER == null){
                    HELPER = new FavoriteFilmHelper(context);
                }
            }
        }
        return HELPER;
    }

    public void open() throws SQLException{
        database = favoriteFilmDatabaseHelper.getWritableDatabase();
    }

    public  void close(){
        favoriteFilmDatabaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                ID + " ASC",
                null);
        cursor.moveToFirst();
        Film film;
        if (cursor.getCount() > 0) {
            do {
                film = new Film();
                film.setFilmId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                film.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                film.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUANGE)));
                film.setVote(cursor.getString(cursor.getColumnIndexOrThrow(VOTE)));
                film.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULAR)));
                film.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                film.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DETAIL)));
                film.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));

                arrayList.add(film);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFilm(Film film){
        ContentValues args = new ContentValues();
        args.put(ID, film.getFilmId());
        args.put(TITLE, film.getTitle());
        args.put(LANGUANGE, film.getLanguage());
        args.put(VOTE, film.getVote());
        args.put(POPULAR, film.getPopularity());
        args.put(DATE, film.getDate());
        args.put(DETAIL, film.getOverview());
        args.put(IMAGE, film.getPhoto());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFilm(int id){
        return database.delete(FAVORITE_FILM_TABLE_NAME, ID + " = '" + id + "'",null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,
                null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID + " ASC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }
}
