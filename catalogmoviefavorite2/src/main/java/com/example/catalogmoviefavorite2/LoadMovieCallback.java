package com.example.catalogmoviefavorite2;

import android.database.Cursor;

public interface LoadMovieCallback {
    void postExecute(Cursor data);
}
