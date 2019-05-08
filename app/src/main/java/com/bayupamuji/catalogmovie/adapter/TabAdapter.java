package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.ui.MoviesFragment;
import com.bayupamuji.catalogmovie.ui.TvShowFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private final String[] title;

    public TabAdapter(FragmentManager fm, Context context) {

        super(fm);
        title = new String[]{
                context.getString(R.string.title_tab1), context.getString(R.string.title_tab2)
        };
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i){
            case 0:
                fragment = new MoviesFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
            default:
                fragment = new MoviesFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
