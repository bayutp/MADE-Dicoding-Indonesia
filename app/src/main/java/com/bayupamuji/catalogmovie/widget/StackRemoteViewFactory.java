package com.bayupamuji.catalogmovie.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.data.DataMovie;
import com.bayupamuji.catalogmovie.database.DatabaseHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<DataMovie> data = new ArrayList<>();
    private final Context context;

    StackRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        DatabaseHelper db = new DatabaseHelper(context);
        data.clear();
        data.addAll(db.getAllMovies());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String url = BuildConfig.BASE_IMAGE_URL+data.get(position).getPoster_path();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        try{
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit(100,150)
                    .get();
            remoteViews.setImageViewBitmap(R.id.iv_movie_widget,bitmap);
            remoteViews.setTextViewText(R.id.tv_name_widget, data.get(position).getTitle());
            remoteViews.setTextViewText(R.id.tv_desc_widget, data.get(position).getOverview());
        }catch (Exception e){
            e.printStackTrace();
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
