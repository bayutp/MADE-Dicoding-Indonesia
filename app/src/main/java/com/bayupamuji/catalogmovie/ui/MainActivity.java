package com.bayupamuji.catalogmovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.adapter.TabAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        viewPagerSetup();
    }

    private void viewPagerSetup() {
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorUnActive),
                getResources().getColor(android.R.color.white));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.view_pager);
        tabLayout= findViewById(R.id.tabs);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}