package com.test.submissionmade2fazri.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.CONTENT_URI;

public class FilmProvider extends ContentProvider{

    private static final  int FILM = 1;
    private static final int FILM_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteFilmHelper favoriteFilmHelper;

    static {
        sUriMatcher.addURI(FavoriteFilmDatabaseContract.AUTHORITY, FavoriteFilmDatabaseContract.FavoriteFilmColumns.FAVORITE_FILM_TABLE_NAME, FILM);
        sUriMatcher.addURI(FavoriteFilmDatabaseContract.AUTHORITY, FavoriteFilmDatabaseContract.FavoriteFilmColumns.FAVORITE_FILM_TABLE_NAME + "/#", FILM_ID);
    }

    @Override
    public boolean onCreate() {
        favoriteFilmHelper = FavoriteFilmHelper.getInstance(getContext());
        favoriteFilmHelper.open();
        return true;
    }



    public FilmProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri,@Nullable String selection,@Nullable String[] selectionArgs) {
        int deletedd;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                deletedd = favoriteFilmHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deletedd = 0;
                break;
        }
        if (deletedd > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deletedd;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,@Nullable ContentValues values) {
        favoriteFilmHelper.open();
        long added;
        switch (sUriMatcher.match(uri)){
            case FILM:
                added = favoriteFilmHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,@Nullable String[] projection,@Nullable String selection,@Nullable String[] selectionArgs,@Nullable String sortOrder) {
        favoriteFilmHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case FILM:
                cursor = favoriteFilmHelper.queryProvider();
                break;
            case FILM_ID:
                cursor = favoriteFilmHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor !=null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return  cursor;
    }

    @Override
    public int update(@NonNull Uri uri,@Nullable ContentValues values,@Nullable String selection, @Nullable String[] selectionArgs) {
        int updatedd;
        switch (sUriMatcher.match(uri)){
            case FILM_ID:
                updatedd = favoriteFilmHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updatedd = 0;
                break;
        }
        if (updatedd > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return updatedd;
    }

}
