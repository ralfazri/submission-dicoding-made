package com.ralfazri.favoriteapk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.ralfazri.favoriteapk.adapter.FilmAdapter;
import com.ralfazri.favoriteapk.db.Film;
import com.ralfazri.favoriteapk.db.LoadCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.ralfazri.favoriteapk.db.FavoriteDatabase.FavoriteFilmColumns.CONTENT_URI;
import static com.ralfazri.favoriteapk.db.MappingHelper.mapCursorFilm;

public class MainActivity extends AppCompatActivity implements LoadCallback {

    private FilmAdapter filmAdapter;
    private RecyclerView recyclerView;
    private HandlerThread handlerThread;
    private Handler handler;
    private DataObserver dataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rc_favorite_film);
        filmAdapter = new FilmAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(filmAdapter);

        handlerThread = new HandlerThread("Dataobserver");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);

        if (savedInstanceState==null){
            new getFilmList(this, this).execute();
        }
    }

    @Override
    public void postExecute(Cursor cursor) {

        ArrayList<Film> dataFilm = mapCursorFilm(cursor);
        if (dataFilm.size() > 0){
            filmAdapter.setFilm(dataFilm);
        }else {
            filmAdapter.setFilm(new ArrayList<Film>());
        }
    }

    private static class getFilmList extends AsyncTask<Void, Void, Cursor>{

        private final WeakReference<Context> weakData;
        private final WeakReference<LoadCallback> weakCallbacks;

        private getFilmList(Context context, LoadCallback loadCallbak){
            weakData = new WeakReference<>(context);
            weakCallbacks = new WeakReference<>(loadCallbak);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakData.get().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallbacks.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver{
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */

        final Context mContext;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.mContext = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getFilmList(mContext, (MainActivity) mContext).execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getFilmList(getApplicationContext(), this).execute();
    }

}
