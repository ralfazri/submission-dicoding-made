package com.test.submissionmade2fazri.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.activity.DetailFilmTvActivity;
import com.test.submissionmade2fazri.model.FilmTv;

import java.util.ArrayList;

public class FavoriteFilmTvAdapter extends RecyclerView.Adapter<FavoriteFilmTvAdapter.ViewHolder> {
    private ArrayList<FilmTv> filmTvs;
    private Context context;

    public ArrayList<FilmTv> getFilmTv(){
        return filmTvs;
    }

    public void setFilmTvs(ArrayList<FilmTv> filmTvs){
        this.filmTvs = filmTvs;
    }

    @NonNull
    @Override
    public FavoriteFilmTvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_tv, parent, false);
        return new FavoriteFilmTvAdapter.ViewHolder(view);
    }
    public FavoriteFilmTvAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<FilmTv> data){
        filmTvs.clear();
        filmTvs.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFilmTvAdapter.ViewHolder holder, final int position) {

        String popularit = Double.toString(filmTvs.get(position).getPopularity());
        String imageDataFilmTv = Constant.API_URL_IMAGE + filmTvs.get(position).getPoster();

        holder.title.setText(filmTvs.get(position).getName());
        holder.popularity.setText(popularit);
        holder.views.setText(filmTvs.get(position).getVote());

        Glide.with(context)
                .load(imageDataFilmTv)
                .placeholder(R.color.colorPrimaryDark)
                .dontAnimate()
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFilmTvActivity.class);
                intent.putExtra(DetailFilmTvActivity.DATA_FILM_TV, getFilmTv().get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmTvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, popularity, views;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_filmTv_detail);
            title = itemView.findViewById(R.id.title_filmTv_item);
            popularity = itemView.findViewById(R.id.rate_filmTv_item);
            views = itemView.findViewById(R.id.views_filmTv_item);
        }
    }
}
