package com.test.submissionmade2fazri.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.db.FavoriteFilmHelper;
import com.test.submissionmade2fazri.model.Film;
import com.test.submissionmade2fazri.widget.ImageWidget;

import java.util.ArrayList;

import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.CONTENT_URI;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.DATE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.DETAIL;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.ID;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.IMAGE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.LANGUANGE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.POPULAR;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.TITLE;
import static com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract.FavoriteFilmColumns.VOTE;

public class DetailFilmActivity extends AppCompatActivity {

    public static final String DATA_FILM = "DATA_FILM" ;
    TextView title, language, views, popularity, date, detail;
    ImageView img;
    private Film film;
    private Menu menu;
    private boolean isFavorite;
    private FavoriteFilmHelper favoriteFilmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        title = findViewById(R.id.tvNameFilmDetail);
        language = findViewById(R.id.tvLanguageFilmDetail);
        views = findViewById(R.id.tvViewsFilmDetail);
        popularity = findViewById(R.id.tvPopularityFilmDetail);
        date = findViewById(R.id.tvDateFilmDetail);
        img = findViewById(R.id.img_potserFilm_detail);
        detail = findViewById(R.id.tvDetail_film_detail);


        film = getIntent().getParcelableExtra(DATA_FILM);


        favoriteFilmHelper = FavoriteFilmHelper.getInstance(getApplicationContext());
        favoriteFilmHelper.open();
        Uri uri = getIntent().getData();
        if (uri!=null){
            Cursor cursor = getContentResolver().query(
                    uri,
                    null,
                    null,
                    null,
                    null);
            if (cursor.moveToFirst()){
                film = new Film(cursor);
                cursor.close();
            }
        }

        isFavorite = false;
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(R.string.detail_film);
        }

        checkFavorite();
        setDetailFilm();
    }

    private void setDetailFilm() {
        if (film != null){
            String popularData = Double.toString(film.getPopularity());
            String imageData = Constant.API_URL_IMAGE + film.getPhoto();

            title.setText(film.getTitle());
            language.setText(film.getLanguage());
            views.setText(film.getVote());
            popularity.setText(popularData);
            date.setText(film.getDate());
            detail.setText(film.getOverview());
            Glide.with(DetailFilmActivity.this)
                    .load(imageData)
                    .placeholder(R.color.colorPrimaryDark)
                    .dontAnimate()
                    .into(img);
        }else {
        }
    }

    private void checkFavorite() {
        ArrayList<Film> filmInDB = favoriteFilmHelper.getAllFilm();

        for (Film film: filmInDB){
            if (this.film.getFilmId() == film.getFilmId()){
                isFavorite = true;
            }

            if (isFavorite == true){
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favor, menu);
        this.menu = menu;

        setIconFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_add_favorite_menu_detail) {
            favoriteButtonPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void favoriteButtonPressed() {
        if (isFavorite){
            favoriteFilmHelper.deleteFilm(film.getFilmId());
            Toast.makeText(this, R.string.remove, Toast.LENGTH_SHORT).show();
            isFavorite = false;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_add_favor));
        }else {
            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();

            ContentValues values = new ContentValues();
            values.put(ID, film.getFilmId());
            values.put(TITLE, film.getTitle());
            values.put(LANGUANGE, film.getLanguage());
            values.put(VOTE, film.getVote());
            values.put(POPULAR, film.getPopularity());
            values.put(DATE, film.getDate());
            values.put(DETAIL, film.getOverview());
            values.put(IMAGE, film.getPhoto());
            getContentResolver().insert(CONTENT_URI, values);
            isFavorite = true;
            updateFavoriteFavoriteStackWidget();
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_added_favor));
        }
    }

    private void setIconFavorite(){
        if (isFavorite) {
            isFavorite = true;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_added_favor));
        } else {
            isFavorite = false;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_add_favor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteFilmHelper.close();
    }

    private void updateFavoriteFavoriteStackWidget(){
        Context context = getApplicationContext();
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, ImageWidget.class);
        int[] idAppWidget = widgetManager.getAppWidgetIds(componentName);
        widgetManager.notifyAppWidgetViewDataChanged(idAppWidget, R.id.stack_view);
    }
}
