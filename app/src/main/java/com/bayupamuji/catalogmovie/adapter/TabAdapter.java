package com.bayupamuji.catalogmovie.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bayupamuji.catalogmovie.ui.MoviesFragment;
import com.bayupamuji.catalogmovie.ui.TvShowFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private final String[] title = new String[]{
            "Movies","Tv Show"
    };

    public TabAdapter(FragmentManager fm) {
        super(fm);
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
