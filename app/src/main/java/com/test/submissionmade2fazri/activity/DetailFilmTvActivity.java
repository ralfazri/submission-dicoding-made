package com.test.submissionmade2fazri.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.db.FavoriteFilmTvHelper;
import com.test.submissionmade2fazri.model.FilmTv;

import java.util.ArrayList;

public class DetailFilmTvActivity extends AppCompatActivity {

    public static final String DATA_FILM_TV = "DATA_FILM_TV";
    private FilmTv filmTv;
    TextView title, date, language, rate, popoular, detail;
    ImageView img;
    private Menu menu;
    private boolean isFavorite;
    private FavoriteFilmTvHelper favoriteFilmTvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film_tv);

        img = findViewById(R.id.img_potserFilmTv_detail);
        title = findViewById(R.id.tvNameFilmTvDetail);
        date = findViewById(R.id.tvDateFilmTvDetail);
        language = findViewById(R.id.tvLanguageFilmTvDetail);
        rate = findViewById(R.id.tvRateFilmTvDetail);
        popoular = findViewById(R.id.tvPopularityFilmTvDetail);
        detail = findViewById(R.id.detail_filmTv_detail);

        filmTv = getIntent().getParcelableExtra(DATA_FILM_TV);

        favoriteFilmTvHelper = FavoriteFilmTvHelper.getInstance(getApplicationContext());
        favoriteFilmTvHelper.open();
        setDetailFilmTv();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.detail_filmtv);
        }

        isFavorite = false;
        checkFavorite();
    }

    private void setDetailFilmTv(){
        if (filmTv !=null){
            String populariData = Double.toString(filmTv.getPopularity());
            String imageData = Constant.API_URL_IMAGE +filmTv.getPoster();

            title.setText(filmTv.getName());
            date.setText(filmTv.getDate());
            language.setText(filmTv.getLanguage());
            rate.setText(filmTv.getVote());
            popoular.setText(populariData);
            detail.setText(filmTv.getOverview());
            Glide.with(DetailFilmTvActivity.this)
                    .load(imageData)
                    .placeholder(R.color.colorPrimaryDark)
                    .dontAnimate()
                    .into(img);
        }else {

        }
    }

    private void checkFavorite() {
        ArrayList<FilmTv> favoriteTvInDB = favoriteFilmTvHelper.getAllFilmTv();

        for (FilmTv filmTv: favoriteTvInDB){
            if (this.filmTv.getTvId() == filmTv.getTvId()){
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


    private void favoriteButtonPressed(){
        if (isFavorite) {
            favoriteFilmTvHelper.deleteFilmTv(filmTv.getTvId());
            Toast.makeText(this, R.string.remove, Toast.LENGTH_SHORT).show();
            isFavorite = false;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_add_favor));
        } else {
            favoriteFilmTvHelper.insertFilmTv(filmTv);
            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
            isFavorite = true;
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_added_favor));
        }
    }

    private void setIconFavorite(){
        if (isFavorite) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_added_favor));
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_add_favor));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteFilmTvHelper.close();
    }
}
