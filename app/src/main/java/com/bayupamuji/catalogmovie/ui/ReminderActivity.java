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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReminderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    AlarmReceiver alarmReceiver;
    RestService restService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        initRest();
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

        Switch switchDaily = findViewById(R.id.switch_daily_reminder);
        Switch switchRelease = findViewById(R.id.switch_new_reminder);

        switchDaily.setOnCheckedChangeListener(this);
        switchRelease.setOnCheckedChangeListener(this);

    }

    private void initRest(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetworkService networkService = retrofit.create(NetworkService.class);
        restService = new RestService(networkService);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.switch_daily_reminder :
                if (isChecked){
                    alarmReceiver.setDailyReminder(this, AlarmReceiver.TYPE_DAILY_REMINDER, "07:00", "Hai, do you want to watch movie today, lets check new movie here");
                }else {
                    alarmReceiver.disableReminder(this, AlarmReceiver.TYPE_DAILY_REMINDER);
                }
                break;
            case R.id.switch_new_reminder :
                if (isChecked){
                    restService.getUpComingMovies(BuildConfig.TMDB_API_KEY, new RestService.MovieCallback() {
                        @Override
                        public void onSuccess(MovieResponse response) {
                            Date date = Calendar.getInstance().getTime();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault());
                            String currentDate = simpleDateFormat.format(date);
                            for (int i=0; i<=response.getMovieList().size(); i++){
                                try {
                                    Date date1 = simpleDateFormat.parse(response.getMovieList().get(i).getRelease_date());
                                    String movieRelease = simpleDateFormat.format(date1);
                                    if (movieRelease.equalsIgnoreCase(currentDate)){
                                        alarmReceiver.setNewMovieReminder(ReminderActivity.this,AlarmReceiver.TYPE_NEW_REMINDER, "00:00","Hai, "+response.getMovieList().get(i).getTitle()+" release today, check it now!");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable error) {
                            Toast.makeText(ReminderActivity.this, "network error : "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
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
