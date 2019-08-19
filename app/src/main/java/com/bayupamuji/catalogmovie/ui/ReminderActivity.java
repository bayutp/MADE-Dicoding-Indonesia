package com.bayupamuji.catalogmovie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.BuildConfig;
import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.network.NetworkService;
import com.bayupamuji.catalogmovie.network.response.MovieResponse;
import com.bayupamuji.catalogmovie.network.response.RestService;
import com.bayupamuji.catalogmovie.scheduller.AlarmReceiver;
import com.bayupamuji.catalogmovie.utils.SharePreferencesCatalogMovie;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private AlarmReceiver alarmReceiver;
    private SharePreferencesCatalogMovie sp;
    private final String KEY_DAILY = "KEY_DAILY";
    private final String KEY_RELEASE = "KEY_RELEASE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_reminder);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.reminder_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initComponent() {
        alarmReceiver = new AlarmReceiver();
        sp = new SharePreferencesCatalogMovie(ReminderActivity.this);
        boolean statusDaily = sp.getStatus(KEY_DAILY);
        boolean statusRelease = sp.getStatus(KEY_RELEASE);
        Switch switchDaily = findViewById(R.id.switch_daily_reminder);
        Switch switchRelease = findViewById(R.id.switch_new_reminder);

        switchDaily.setOnCheckedChangeListener(this);
        switchRelease.setOnCheckedChangeListener(this);

        switchDaily.setChecked(statusDaily);
        switchRelease.setChecked(statusRelease);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_daily_reminder:
                if (isChecked) {
                    sp.setStatus(KEY_DAILY);
                    alarmReceiver.setDailyReminder(this, AlarmReceiver.TYPE_DAILY_REMINDER, "07:00", "Hai, do you want to watch movie today, lets check it now!");
                } else {
                    sp.remove(KEY_DAILY);
                    alarmReceiver.disableReminder(this, AlarmReceiver.TYPE_DAILY_REMINDER);
                }
                break;
            case R.id.switch_new_reminder:
                if (isChecked) {
                    sp.setStatus(KEY_RELEASE);
                    alarmReceiver.setNewMovieReminder(this, AlarmReceiver.TYPE_NEW_REMINDER,"08:00");
                } else {
                    sp.remove(KEY_RELEASE);
                    alarmReceiver.disableReminder(this, AlarmReceiver.TYPE_NEW_REMINDER);
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }
}
