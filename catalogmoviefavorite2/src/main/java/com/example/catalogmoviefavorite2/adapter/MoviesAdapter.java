package com.example.catalogmoviefavorite2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catalogmoviefavorite2.R;
import com.example.catalogmoviefavorite2.data.DataMovie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private List<DataMovie> listMovie = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        viewHolder.bind(listMovie.get(i),context);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(listMovie.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMovie != null) {
            return listMovie.size();
        }else {
            return 0;
        }
    }

    public void updateMovie(List<DataMovie> dataMovieList){
        listMovie.clear();
        listMovie.addAll(dataMovieList);
        notifyDataSetChanged();
    }

}
