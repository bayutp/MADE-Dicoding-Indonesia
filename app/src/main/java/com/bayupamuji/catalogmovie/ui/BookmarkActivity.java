package com.bayupamuji.catalogmovie.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.adapter.TabAdapter;

public class BookmarkActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        initView();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.bookmark));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_bookmark);
        ViewPager viewPager = findViewById(R.id.view_pager_bookmark);
        TabLayout tabLayout = findViewById(R.id.tabs_bookmark);

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),this,
                new MoviesBookmarkFragment(), new TvFavoriteFragment()));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorUnActive),
                getResources().getColor(android.R.color.white));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
