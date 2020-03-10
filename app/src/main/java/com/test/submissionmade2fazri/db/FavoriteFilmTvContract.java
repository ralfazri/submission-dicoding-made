package com.test.submissionmade2fazri.db;

import android.provider.BaseColumns;

public class FavoriteFilmTvContract {

    static final class FavoriteFilmTvColumns implements BaseColumns{
        static final String FAVORITE_FILM_TV_TABLE_NAME = "favoritefilmtv";

        static final String ID = "id";
        static final String NAME = "name";
        static final String DATE = "first_air_date";
        static final String LANGUANGE = "original_language";
        static final String VOTE = "vote_average";
        static final String POPULARITY = "popularity";
        static final String OVERVIEW = "overview";
        static final String POSTER_PATH = "poster_path";

    }
}
