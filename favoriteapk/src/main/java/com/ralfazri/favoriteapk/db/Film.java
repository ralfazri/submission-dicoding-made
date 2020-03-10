package com.ralfazri.favoriteapk.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Film implements Parcelable {

    private int filmId;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }


    private Double popularity;
    public String title;
    private String language;
    private String overview;
    private String date;
    private String photo;
    private String banner;
    private String vote;

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    private Double vote_fix;

    public Double getVote_fix() {
        return vote_fix;
    }

    public void setVote_fix(Double vote_fix) {
        this.vote_fix = vote_fix;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Film(){}

    public Film(JSONObject object){
        try {
            int id = object.getInt("id");
            Double popularity = object.getDouble("popularity");
            String title = object.getString("title");
            String language = object.getString("original_language");
            String overview = object.getString("overview");
            String date= object.getString("release_date");
            String photo = object.getString("poster_path");
            String banner = object.getString("backdrop_path");
            String vote = object.getString("vote_average");
            Double vote_fix = object.getDouble("vote_count");

            this.filmId = id;
            this.popularity = popularity;
            this.title = title;
            this.language = language;
            this.overview = overview;
            this.date = date;
            this.photo = photo;
            this.banner = banner;
            this.vote = vote;
            this.vote_fix =vote_fix;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.filmId);
        dest.writeValue(this.popularity);
        dest.writeString(this.title);
        dest.writeString(this.language);
        dest.writeString(this.overview);
        dest.writeString(this.date);
        dest.writeString(this.photo);
        dest.writeString(this.banner);
        dest.writeString(this.vote);
        dest.writeValue(this.vote_fix);
    }
    protected Film(Parcel parcel){
        this.filmId = parcel.readInt();
        this.popularity = (Double) parcel.readValue(Double.class.getClassLoader());
        this.title = parcel.readString();
        this.language = parcel.readString();
        this.overview = parcel.readString();
        this.date = parcel.readString();
        this.photo = parcel.readString();
        this.banner = parcel.readString();
        this.vote = parcel.readString();
        this.vote_fix = (Double) parcel.readValue(Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
