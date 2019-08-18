package com.example.catalogmoviefavorite2.util;

import android.database.Cursor;

import com.example.catalogmoviefavorite2.data.DataMovie;

import java.util.ArrayList;

public class MapingHelper {
    public static ArrayList<DataMovie> mapCursorToArrayList(Cursor cursor) {
        ArrayList<DataMovie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_OVERVIEW));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_RELEASE));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_POSTER_PATH));
            movieList.add(new DataMovie(id, title, posterPath, description, date));
        }
        return movieList;
    }
}
