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
import com.bumptech.glide.request.RequestOptions;
import com.test.submissionmade2fazri.Constant;
import com.test.submissionmade2fazri.activity.DetailFilmTvActivity;
import com.test.submissionmade2fazri.R;
import com.test.submissionmade2fazri.model.Film;
import com.test.submissionmade2fazri.model.FilmTv;

import java.util.ArrayList;

public class FilmTVAdapter extends RecyclerView.Adapter<FilmTVAdapter.FilmTVViewHolder> {

    private ArrayList<FilmTv> dFilmTv = new ArrayList<>();

    private Context context;

    public FilmTVAdapter(Context context){
        this.context = context;
    }

    public ArrayList<FilmTv> getFilmTv(){
        return dFilmTv;
    }

    public void setFilmTvData(ArrayList<FilmTv> dataFilmTV){
        dFilmTv.clear();
        dFilmTv.addAll(dataFilmTV);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FilmTVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_tv, parent,false);
        return new FilmTVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmTVViewHolder holder, int position) {
        holder.bind(dFilmTv.get(position));
    }

    @Override
    public int getItemCount() {
        return dFilmTv.size();
    }

    class FilmTVViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        ImageView img;
        TextView title, popularity, views;


        FilmTVViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_filmTv_item);
            popularity = itemView.findViewById(R.id.rate_filmTv_item);
            views = itemView.findViewById(R.id.views_filmTv_item);
            img = itemView.findViewById(R.id.img_filmTv_detail);

            itemView.setOnClickListener(this);
        }

        void bind(FilmTv filmTv) {
            String popularit = Double.toString(filmTv.getPopularity());
            String imageDataFilmTv = Constant.API_URL_IMAGE + filmTv.getPoster();

            title.setText(filmTv.getName());
            popularity.setText(popularit);
            views.setText(filmTv.getVote());

            Glide.with(itemView.getContext())
                    .load(imageDataFilmTv)
                    .placeholder(R.color.colorPrimaryDark)
                    .dontAnimate()
                    .into(img);
        }

        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            FilmTv filmTv = dFilmTv.get(i);

            filmTv.setName(filmTv.getName());

            Intent intent = new Intent(itemView.getContext(), DetailFilmTvActivity.class);
            intent.putExtra(DetailFilmTvActivity.DATA_FILM_TV, getFilmTv().get(i));
            itemView.getContext().startActivity(intent);

        }


    }
}
