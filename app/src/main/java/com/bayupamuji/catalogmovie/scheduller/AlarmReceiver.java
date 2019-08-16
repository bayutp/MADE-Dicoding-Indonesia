package com.bayupamuji.catalogmovie.scheduller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.bayupamuji.catalogmovie.R;
import com.bayupamuji.catalogmovie.ui.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY_REMINDER = "DAILY_REMINDER";
    public static final String TYPE_NEW_REMINDER = "NEW_REMINDER";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String CHANNEL_ID = "Channel_01";

    public final int ID_DAILY_REMINDER = 1;
    public final int ID_NEW_REMINDER = 2;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(TYPE_DAILY_REMINDER);
        int notifId = type.equalsIgnoreCase(TYPE_DAILY_REMINDER) ? ID_DAILY_REMINDER : ID_NEW_REMINDER;
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_DAILY_REMINDER)? TYPE_DAILY_REMINDER : TYPE_NEW_REMINDER;

        sendNotification(context, notifId, title, message);
    }

    private void sendNotification(Context context, int notifId, String title, String message) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context,notifId,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(title)
                .setContentText(message)
                .setAutoCancel(true);


    }



}
