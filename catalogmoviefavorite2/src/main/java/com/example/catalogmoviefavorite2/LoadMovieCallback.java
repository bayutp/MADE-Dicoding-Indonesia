package com.example.catalogmoviefavorite2;

import android.database.Cursor;

interface LoadMovieCallback {
    void postExecute(Cursor data);
}
