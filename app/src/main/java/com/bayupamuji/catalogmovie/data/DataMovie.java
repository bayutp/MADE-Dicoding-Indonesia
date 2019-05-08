package com.bayupamuji.catalogmovie.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataMovie implements Parcelable {
    @SerializedName("id")
    private final String id;

    @SerializedName("title")
    private final String title;

    @SerializedName("poster_path")
    private final String poster_path;

    @SerializedName("overview")
    private final String overview;


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
}
