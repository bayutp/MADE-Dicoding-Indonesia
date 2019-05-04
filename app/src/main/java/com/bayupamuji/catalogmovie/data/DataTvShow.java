package com.bayupamuji.catalogmovie.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataTvShow implements Parcelable {
    @SerializedName("name")
    private final String name;

    @SerializedName("poster_path")
    private final String poster_path;

    @SerializedName("overview")
    private final String overview;

    public DataTvShow(String name, String poster_path, String overview) {
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    private DataTvShow(Parcel in) {
        name = in.readString();
        poster_path = in.readString();
        overview = in.readString();
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

    public String getName() {
        return name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(poster_path);
        dest.writeString(overview);
    }
}
