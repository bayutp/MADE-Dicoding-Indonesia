package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;

import java.util.List;

public class MoviesAdapter extends BaseAdapter {
    private final Context context;
    private final List<DataMovie> dataMovies;

    public MoviesAdapter(Context context, List<DataMovie> dataMovies) {
        this.context = context;
        this.dataMovies = dataMovies;
    }

    @Override
    public int getCount() {
        return dataMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return dataMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(convertView);
        DataMovie dataMovie = (DataMovie) getItem(position);
        viewHolder.bind(dataMovie, context);
        return convertView;
    }
}
