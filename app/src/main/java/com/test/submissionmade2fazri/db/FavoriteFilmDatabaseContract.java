package com.test.submissionmade2fazri.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteFilmDatabaseContract {
    public static final String AUTHORITY = "com.test.submissionmade2fazri";
    public static final String SCHEME = "content";


    public static final class FavoriteFilmColumns implements BaseColumns{
        public static final String FAVORITE_FILM_TABLE_NAME = "favoritefilm";

        public static String ID = "id";
        public static final String TITLE = "title";
        public static final String LANGUANGE = "original_laguange";
        public static final String VOTE = "vote_average";
        public static final String POPULAR = "popularity";
        public static final String DATE = "release_date";
        public static final String DETAIL = "overview";
        public static final String IMAGE = "poster_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_FILM_TABLE_NAME)
                .build();
    }

    public static String getColumnString(Cursor cursor, String column){
        return cursor.getString(cursor.getColumnIndex(column));
    }

    public static  int getColumnInt(Cursor cursor, String column){
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    public static  Double getColumnDouble(Cursor cursor, String column){
        return cursor.getDouble(cursor.getColumnIndex(column));
    }
}
