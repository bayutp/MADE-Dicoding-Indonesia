package com.example.catalogmoviefavorite2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.catalogmoviefavorite2.data.DataMovie;
import com.example.catalogmoviefavorite2.data.DataTvShow;

import java.util.ArrayList;
import java.util.List;

import static com.example.catalogmoviefavorite2.data.DataMovie.COLUMN_ID;
import static com.example.catalogmoviefavorite2.data.DataMovie.COLUMN_OVERVIEW;
import static com.example.catalogmoviefavorite2.data.DataMovie.COLUMN_POSTER_PATH;
import static com.example.catalogmoviefavorite2.data.DataMovie.COLUMN_RELEASE;
import static com.example.catalogmoviefavorite2.data.DataMovie.COLUMN_TITLE;
import static com.example.catalogmoviefavorite2.data.DataMovie.TABLE_NAME;
import static com.example.catalogmoviefavorite2.data.DataTvShow.COLUMN_TV_ID;
import static com.example.catalogmoviefavorite2.data.DataTvShow.COLUMN_TV_NAME;
import static com.example.catalogmoviefavorite2.data.DataTvShow.COLUMN_TV_OVERVIEW;
import static com.example.catalogmoviefavorite2.data.DataTvShow.COLUMN_TV_POSTER_PATH;
import static com.example.catalogmoviefavorite2.data.DataTvShow.COLUMN_TV_RELEASE;
import static com.example.catalogmoviefavorite2.data.DataTvShow.TABLE_TV_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies_db";
    private static final String AUTHOR = "dicoding.com";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHOR)
            .appendPath(DataMovie.TABLE_NAME)
            .build();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataMovie.CREATE_TABLE);
        db.execSQL(DataTvShow.CREATE_TV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataMovie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataTvShow.TABLE_TV_NAME);
        onCreate(db);
    }

    public void insertMovies(String id, String title, String path, String overview, String release_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, id);
        values.put(COLUMN_TITLE, title);
        values.put(DataMovie.COLUMN_POSTER_PATH, path);
        values.put(DataMovie.COLUMN_OVERVIEW, overview);
        values.put(COLUMN_RELEASE, release_date);

        db.insert(DataMovie.TABLE_NAME, null, values);
        db.close();
    }

    public DataMovie getMovie(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DataMovie.TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_OVERVIEW, COLUMN_POSTER_PATH, COLUMN_RELEASE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        DataMovie data = new DataMovie();
        if (cursor != null && cursor.moveToFirst()) {
            data = new DataMovie(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE))
            );
            cursor.close();
        }

        return data;
    }

    public List<DataMovie> getAllMovies() {
        List<DataMovie> data = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DataMovie movie = new DataMovie();
                movie.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RELEASE)));
                data.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    public Cursor getAllMovieProvider() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor getMovieProvider(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_OVERVIEW, COLUMN_POSTER_PATH, COLUMN_RELEASE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
    }

    public long insertMovieProvider(ContentValues values){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.insert(TABLE_NAME, null, values);
    }

    public int deleteMovieProvider(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_NAME,COLUMN_ID + " = ?", new String[]{id});
    }

    public void deleteMovie(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }

    public void insertTvShow(String id, String name, String path, String overview, String release_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TV_ID, id);
        values.put(COLUMN_TV_NAME, name);
        values.put(COLUMN_TV_POSTER_PATH, path);
        values.put(COLUMN_TV_OVERVIEW, overview);
        values.put(COLUMN_TV_RELEASE,release_date);

        db.insert(TABLE_TV_NAME, null, values);
        db.close();
    }

    public DataTvShow getTvShow(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TV_NAME,
                new String[]{COLUMN_TV_ID, COLUMN_TV_NAME, COLUMN_TV_POSTER_PATH, COLUMN_TV_OVERVIEW, COLUMN_TV_RELEASE},
                COLUMN_TV_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        DataTvShow data = new DataTvShow();
        if (cursor != null && cursor.moveToFirst()) {
            data = new DataTvShow(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_OVERVIEW)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_RELEASE))
            );
            cursor.close();
        }

        return data;
    }

    public List<DataTvShow> getAllTvShow() {
        List<DataTvShow> dataList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_TV_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DataTvShow data = new DataTvShow();
                data.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_ID)));
                data.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_NAME)));
                data.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_POSTER_PATH)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_OVERVIEW)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_RELEASE)));
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataList;
    }

    public void deleteTvShow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TV_NAME, COLUMN_TV_ID + " = ?", new String[]{id});
        db.close();
    }
}