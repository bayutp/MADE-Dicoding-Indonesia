package com.bayupamuji.catalogmovie.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataMovie implements Parcelable {
    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_OVERVIEW = "overview";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_POSTER_PATH + " TEXT,"
                    + COLUMN_OVERVIEW + " TEXT)";

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("overview")
    private String overview;


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private DataMovie(Parcel in) {
        id = in.readString();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
    }

    public static final Creator<DataMovie> CREATOR = new Creator<DataMovie>() {
        @Override
        public DataMovie createFromParcel(Parcel in) {
            return new DataMovie(in);
        }

        @Override
        public DataMovie[] newArray(int size) {
            return new DataMovie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
    }

    public DataMovie(String id, String title, String poster_path, String overview) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public DataMovie() {
    }
}
