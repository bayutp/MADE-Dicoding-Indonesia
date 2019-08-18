package com.example.catalogmoviefavorite2;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.catalogmoviefavorite2.adapter.ItemClickListener;
import com.example.catalogmoviefavorite2.adapter.MoviesAdapter;
import com.example.catalogmoviefavorite2.data.DataMovie;
import com.example.catalogmoviefavorite2.database.DatabaseHelper;
import com.example.catalogmoviefavorite2.util.MapingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieCallback, ItemClickListener {

    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetData(this,this).execute();
        initComponent();
    }

    private void initComponent() {
        adapter = new MoviesAdapter(this, this);
        RecyclerView rvBookmark = findViewById(R.id.rv_bookmark);
        rvBookmark.setLayoutManager(new LinearLayoutManager(this));
        rvBookmark.setHasFixedSize(true);
        rvBookmark.setAdapter(adapter);
    }

    @Override
    public void postExecute(Cursor data) {
        ArrayList<DataMovie> listMovie = MapingHelper.mapCursorToArrayList(data);

        if (listMovie.size() > 0){
            adapter.updateMovie(listMovie);
        }
    }

    @Override
    public void onItemClick(DataMovie dataMovie) {
        Toast.makeText(this, "Movie "+dataMovie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private static class GetData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private GetData(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(DatabaseHelper.CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }
}
