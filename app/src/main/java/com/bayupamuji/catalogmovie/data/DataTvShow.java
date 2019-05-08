package com.bayupamuji.catalogmovie.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataTvShow implements Parcelable {
    @SerializedName("id")
    private final String id;

    @SerializedName("name")
    private final String name;

    @SerializedName("poster_path")
    private final String poster_path;

    @SerializedName("overview")
    private final String overview;


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

    private DataTvShow(Parcel in) {
        id = in.readString();
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
    }
}
