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

public class SearchFilmTvAdapter extends RecyclerView.Adapter<SearchFilmTvAdapter.ViewHolder> {


    private ArrayList<FilmTv> filmTvArrayList = new ArrayList<>();
    private Context context;

    public ArrayList<FilmTv> getFilmTv(){
        return filmTvArrayList;
    }

    public void setFilm(ArrayList<FilmTv> filmTvs){
        this.filmTvArrayList = filmTvs;
    }

    public SearchFilmTvAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<FilmTv> data){
        filmTvArrayList.clear();
        filmTvArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchFilmTvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film_tv, parent, false);
        return new SearchFilmTvAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilmTvAdapter.ViewHolder holder, int position) {
        holder.bind(filmTvArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return filmTvArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView title, popularity, views;


        public ViewHolder(@NonNull View itemView) {
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
            FilmTv filmTv = filmTvArrayList.get(i);

            filmTv.setName(filmTv.getName());

            Intent intent = new Intent(itemView.getContext(), DetailFilmTvActivity.class);
            intent.putExtra(DetailFilmTvActivity.DATA_FILM_TV, getFilmTv().get(i));
            itemView.getContext().startActivity(intent);

        }
    }
}
