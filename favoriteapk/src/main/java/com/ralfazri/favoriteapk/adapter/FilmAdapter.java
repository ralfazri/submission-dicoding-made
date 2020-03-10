package com.ralfazri.favoriteapk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ralfazri.favoriteapk.Constant;
import com.ralfazri.favoriteapk.R;
import com.ralfazri.favoriteapk.db.Film;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private Context mContext;
    private ArrayList<Film> filmArrayList = new ArrayList<>();

    public FilmAdapter(Context context){
        this.mContext = context;
    }

    public void setFilm(ArrayList<Film> data){
        this.filmArrayList.clear();
        this.filmArrayList.addAll(data);
        notifyDataSetChanged();
    }

    private ArrayList<Film> getFilm(){
        return filmArrayList;
    }

    @NonNull
    @Override
    public FilmAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.FilmViewHolder holder, int position) {
        holder.bind(filmArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return getFilm().size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tvJudul, tvRate, tvPopular;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_film_detail);
            tvJudul = itemView.findViewById(R.id.title_film_item);
            tvRate = itemView.findViewById(R.id.rate_film_item);
            tvPopular = itemView.findViewById(R.id.views_film_item);
        }

        public void bind(final Film film) {
            String popouler = Double.toString(film.getPopularity());
            String  imageData = Constant.API_URL_IMAGE + film.getPhoto();

            tvJudul.setText(film.getTitle());
            tvRate.setText(popouler);
            tvPopular.setText(film.getVote());
            Glide.with(mContext)
                    .load(imageData)
                    .into(img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Anda menyukai : " + film.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
