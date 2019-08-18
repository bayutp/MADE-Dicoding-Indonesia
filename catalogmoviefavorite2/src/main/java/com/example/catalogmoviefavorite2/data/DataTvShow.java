package com.example.catalogmoviefavorite2.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataTvShow implements Parcelable {
    public static final String TABLE_TV_NAME = "tv_show";

    public static final String COLUMN_TV_ID = "id";
    public static final String COLUMN_TV_NAME = "name";
    public static final String COLUMN_TV_POSTER_PATH = "poster_path";
    public static final String COLUMN_TV_OVERVIEW = "overview";
    public static final String COLUMN_TV_RELEASE = "release_date";

    public static final String CREATE_TV_TABLE =
            "CREATE TABLE " + TABLE_TV_NAME + "("
                    + COLUMN_TV_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TV_NAME + " TEXT,"
                    + COLUMN_TV_POSTER_PATH + " TEXT,"
                    + COLUMN_TV_OVERVIEW + " TEXT,"
                    + COLUMN_TV_RELEASE + " TEXT)";

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private DataTvShow(Parcel in) {
        id = in.readString();
        name = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<DataTvShow> CREATOR = new Creator<DataTvShow>() {
        @Override
        public DataTvShow createFromParcel(Parcel in) {
            return new DataTvShow(in);
        }

        @Override
        public DataTvShow[] newArray(int size) {
            return new DataTvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
    }

    public DataTvShow(String id, String name, String poster_path, String overview,String release_date) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public DataTvShow() {
    }
}
