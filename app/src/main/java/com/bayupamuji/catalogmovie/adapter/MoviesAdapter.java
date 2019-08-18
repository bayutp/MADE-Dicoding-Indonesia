package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private Cursor cursor;
    private final ItemClickListener itemClickListener;

    public MoviesAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final List<DataMovie> movieList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                DataMovie data = new DataMovie();
                data.setId(cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_ID)));
                data.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_TITLE)));
                data.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_POSTER_PATH)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_OVERVIEW)));
                data.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DataMovie.COLUMN_RELEASE)));
                movieList.add(data);
            } while (cursor.moveToNext());
        }
        viewHolder.bind(movieList.get(i),context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(movieList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }else {
            return 0;
        }
    }

    public void updateMovie(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

}
