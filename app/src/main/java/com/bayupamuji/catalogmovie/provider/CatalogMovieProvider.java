package com.bayupamuji.catalogmovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.database.DatabaseHelper;

import java.util.Objects;

import static com.bayupamuji.catalogmovie.database.DatabaseHelper.AUTHOR;

public class CatalogMovieProvider extends ContentProvider {
    public static final int MOVIE = 1;
    public static final int MOVIE_ID = 2;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DatabaseHelper dbHelper;

    static {
        uriMatcher.addURI(AUTHOR, DataMovie.TABLE_NAME, MOVIE);
        uriMatcher.addURI(AUTHOR + "/", DataMovie.TABLE_NAME, MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Cursor query(@NonNull Uri uri, @NonNull String[] strings, @NonNull String s, @NonNull String[] strings1, @NonNull String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = dbHelper.getAllMovieProvider();
                break;
            case MOVIE_ID:
                cursor = dbHelper.getMovieProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;

        }
        if (cursor != null)
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(),uri);

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Uri insert(@NonNull Uri uri, @NonNull ContentValues contentValues) {
        long added;
        if (uriMatcher.match(uri) == MOVIE) {
            added = dbHelper.insertMovieProvider(contentValues);
        } else {
            added = 0;
        }
        if (added > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseHelper.CONTENT_URI, null);

        return Uri.parse(DatabaseHelper.CONTENT_URI + "/" + added);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int delete(@NonNull Uri uri, @NonNull String s, @NonNull String[] strings) {
        int deleted;
        if (uriMatcher.match(uri) == MOVIE_ID) {
            deleted = dbHelper.deleteMovieProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }
        if (deleted > 0)
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DatabaseHelper.CONTENT_URI, null);

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @NonNull ContentValues contentValues, @NonNull String s, @NonNull String[] strings) {
        return 0;
    }
}
