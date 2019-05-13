package com.bayupamuji.catalogmovie.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bayupamuji.catalogmovie.R;

public class TabAdapter extends FragmentPagerAdapter {

    private final String[] title;
    private final Fragment frag1;
    private final Fragment frag2;

    public TabAdapter(FragmentManager fm, Context context, Fragment frag1, Fragment frag2) {

        super(fm);
        title = new String[]{
                context.getString(R.string.title_tab1), context.getString(R.string.title_tab2)
        };
        this.frag1 = frag1;
        this.frag2 = frag2;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i){
            case 0:
                fragment = frag1;
                break;
            case 1:
                fragment = frag2;
                break;
            default:
                fragment = frag1;
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
