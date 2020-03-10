package com.ralfazri.favoriteapk.db;

import android.database.Cursor;

public interface LoadCallback {

    void postExecute(Cursor cursor);
}
