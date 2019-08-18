package com.example.catalogmoviefavorite2;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.catalogmoviefavorite2.data.DataMovie;
import com.example.catalogmoviefavorite2.database.DatabaseHelper;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetData().execute();

    }

    private class GetData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(DatabaseHelper.CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            Log.d("cel data","data bookmark >>>>>"+ new Gson().toJsonTree(new DataMovie(data)));
        }
    }
}
