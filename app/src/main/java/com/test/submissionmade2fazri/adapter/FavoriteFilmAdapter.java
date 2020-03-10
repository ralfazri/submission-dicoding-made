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
import com.test.submissionmade2fazri.activity.DetailFilmActivity;
import com.test.submissionmade2fazri.model.Film;

import java.util.ArrayList;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.ViewHolder> {

    private ArrayList<Film> films;
    private Context context;

    public ArrayList<Film> getFilms(){
        return films;
    }

    public void setFilms(ArrayList<Film> films){
        this.films = films;
    }

    public FavoriteFilmAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Film> data){
        films.clear();
        films.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoriteFilmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FavoriteFilmAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFilmAdapter.ViewHolder holder, final int position) {
        String populer = Double.toString(films.get(position).getPopularity());
        String  imageData = Constant.API_URL_IMAGE + films.get(position).getPhoto();

        holder.tittle.setText(films.get(position).getTitle());
        holder.popularity.setText(populer);
        holder.view.setText(films.get(position).getVote());
        Glide.with(context)
                .load(imageData)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFilmActivity.class);
                intent.putExtra(DetailFilmActivity.DATA_FILM, getFilms().get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tittle, popularity, view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_film_detail);
            tittle =  itemView.findViewById(R.id.title_film_item);
            popularity = itemView.findViewById(R.id.rate_film_item);
            view = itemView.findViewById(R.id.views_film_item);
        }
    }
}
