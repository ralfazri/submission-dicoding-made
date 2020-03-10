package com.test.submissionmade2fazri.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class FilmTv implements Parcelable {

    private int tvId;

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    private Double popularity;
    private Double vote_fix;
    private String vote;
    private String name;
    private String language;
    private String date;
    private String overview;
    private String poster;



    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVote_fix() {
        return vote_fix;
    }

    public void setVote_fix(Double vote_fix) {
        this.vote_fix = vote_fix;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public FilmTv(){

    }

    public FilmTv(JSONObject object){
        try {
            int tvId = object.getInt("id");
            Double popularity = object.getDouble("popularity");
            Double vote_fix = object.getDouble("vote_count");
            String vote = object.getString("vote_average");
            String name = object.getString("name");
            String language = object.getString("original_language");
            String date = object.getString("first_air_date");
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");

            this.tvId = tvId;
            this.popularity = popularity;
            this.vote_fix = vote_fix;
            this.vote = vote;
            this.name = name;
            this.language = language;
            this.date = date;
            this.overview = overview;
            this.poster = poster;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tvId);
        dest.writeValue(this.popularity);
        dest.writeValue(this.vote_fix);
        dest.writeString(this.vote);
        dest.writeString(this.name);
        dest.writeString(this.language);
        dest.writeString(this.date);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
    }

    protected FilmTv(Parcel parcel){
        this.tvId = parcel.readInt();
        this.popularity = (Double) parcel.readValue(Double.class.getClassLoader());
        this.vote_fix = (Double) parcel.readValue(Double.class.getClassLoader());
        this.vote = parcel.readString();
        this.name = parcel.readString();
        this.language = parcel.readString();
        this.date = parcel.readString();
        this.overview = parcel.readString();
        this.poster = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilmTv> CREATOR = new Creator<FilmTv>() {
        @Override
        public FilmTv createFromParcel(Parcel source) {
            return new FilmTv(source);
        }

        @Override
        public FilmTv[] newArray(int size) {
            return new FilmTv[size];
        }
    };
}
