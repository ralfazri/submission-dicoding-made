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
import com.test.submissionmade2fazri.activity.DetailFilmActivity;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.model.Film;

import java.util.ArrayList;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private ArrayList<Film> dFilm = new ArrayList<>();

    public ArrayList<Film> getFilm(){
        return dFilm;
    }
    private Context context;

    public void setDataFilm(ArrayList<Film> dataFilm) {
        dFilm.clear();
        dFilm.addAll(dataFilm);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(dFilm.get(position));
    }

    public FilmAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return dFilm.size();
    }

    class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView  tittle, popularity, view;


        FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_film_detail);
            tittle =  itemView.findViewById(R.id.title_film_item);
            popularity = itemView.findViewById(R.id.rate_film_item);
            view = itemView.findViewById(R.id.views_film_item);

            itemView.setOnClickListener(this);
        }

        void bind(Film film){
            String populer = Double.toString(film.getPopularity());
            String  imageData = Constant.API_URL_IMAGE + film.getPhoto();

            tittle.setText(film.getTitle());
            popularity.setText(populer);
            view.setText(film.getVote());

            Glide.with(itemView.getContext())
                    .load(imageData)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(img);
        }

        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            Film film = dFilm.get(i);

            film.setTitle(film.getTitle());

            Intent intent = new Intent(itemView.getContext(), DetailFilmActivity.class);
            intent.putExtra(DetailFilmActivity.DATA_FILM, getFilm().get(i));
            itemView.getContext().startActivity(intent);

        }
    }


}
