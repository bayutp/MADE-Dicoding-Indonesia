package com.bayupamuji.catalogmovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.data.DataTvShow;

import java.util.ArrayList;
import java.util.List;

import static com.bayupamuji.catalogmovie.data.DataMovie.COLUMN_ID;
import static com.bayupamuji.catalogmovie.data.DataMovie.COLUMN_OVERVIEW;
import static com.bayupamuji.catalogmovie.data.DataMovie.COLUMN_POSTER_PATH;
import static com.bayupamuji.catalogmovie.data.DataMovie.COLUMN_TITLE;
import static com.bayupamuji.catalogmovie.data.DataMovie.TABLE_NAME;
import static com.bayupamuji.catalogmovie.data.DataTvShow.COLUMN_TV_ID;
import static com.bayupamuji.catalogmovie.data.DataTvShow.COLUMN_TV_NAME;
import static com.bayupamuji.catalogmovie.data.DataTvShow.COLUMN_TV_OVERVIEW;
import static com.bayupamuji.catalogmovie.data.DataTvShow.COLUMN_TV_POSTER_PATH;
import static com.bayupamuji.catalogmovie.data.DataTvShow.TABLE_TV_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataMovie.CREATE_TABLE);
        db.execSQL(DataTvShow.CREATE_TV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DataMovie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DataTvShow.TABLE_TV_NAME);
        onCreate(db);
    }

    public void insertMovies(String id, String title, String path, String overview){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID,id);
        values.put(COLUMN_TITLE,title);
        values.put(DataMovie.COLUMN_POSTER_PATH,path);
        values.put(DataMovie.COLUMN_OVERVIEW,overview);

        db.insert(DataMovie.TABLE_NAME,null,values);
        db.close();
    }

    public DataMovie getMovie(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DataMovie.TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_OVERVIEW,COLUMN_POSTER_PATH},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        DataMovie data = new DataMovie();
        if (cursor !=null && cursor.moveToFirst()){
             data = new DataMovie(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW))
            );
            cursor.close();
        }

        return data;
    }

    public List<DataMovie> getAllMovies(){
        List<DataMovie> data = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                DataMovie movie = new DataMovie();
                movie.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movie.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OVERVIEW)));
                data.add(movie);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return data;
    }

    public void deleteMovie(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID +" = ?", new String[]{id});
        db.close();
    }

    public void insertTvShow(String id, String name, String path, String overview){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TV_ID,id);
        values.put(COLUMN_TV_NAME,name);
        values.put(COLUMN_TV_POSTER_PATH,path);
        values.put(COLUMN_TV_OVERVIEW,overview);

        db.insert(TABLE_TV_NAME,null,values);
        db.close();
    }

    public DataTvShow getTvShow(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TV_NAME,
                new String[]{COLUMN_TV_ID, COLUMN_TV_NAME,COLUMN_TV_POSTER_PATH,COLUMN_TV_OVERVIEW},
                COLUMN_TV_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        DataTvShow data = new DataTvShow();
        if (cursor !=null && cursor.moveToFirst()){
             data = new DataTvShow(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_POSTER_PATH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_OVERVIEW))
            );
            cursor.close();
        }

        return data;
    }

    public List<DataTvShow> getAllTvShow(){
        List<DataTvShow> dataList = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_TV_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                DataTvShow data = new DataTvShow();
                data.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_ID)));
                data.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_NAME)));
                data.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_POSTER_PATH)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TV_OVERVIEW)));
                dataList.add(data);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataList;
    }

    public void deleteTvShow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TV_NAME, COLUMN_TV_ID +" = ?", new String[]{id});
        db.close();
    }
}
