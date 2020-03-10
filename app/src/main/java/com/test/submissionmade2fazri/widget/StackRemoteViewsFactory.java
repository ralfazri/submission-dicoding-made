package com.test.submissionmade2fazri.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.db.FavoriteFilmDatabaseContract;
import com.test.submissionmade2fazri.db.MappingHelper;
import com.test.submissionmade2fazri.model.Film;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<Film> films;
    private Context mContext;
    private Cursor cursor;

    public StackRemoteViewsFactory(Context context){
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                FavoriteFilmDatabaseContract.FavoriteFilmColumns.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        films = MappingHelper.mapCursorFilm(cursor);

        for (Film filmData: films){
            try {
                String url = "https://image.tmdb.org/t/p/w500" + filmData.getPhoto();
                Bitmap bitmap = Glide.with(mContext)
                        .asBitmap()
                        .load(url)
                        .submit(521, 512)
                        .get();
                bitmaps.add(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null){
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (bitmaps == null){
            return 0;
        }else {
            return bitmaps.size();
        }

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.image_item);
        remoteViews.setImageViewBitmap(R.id.image_item_widget, bitmaps.get(position));

        Bundle bundle = new Bundle();
        bundle.putInt(ImageWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.image_item_widget, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong( 0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
