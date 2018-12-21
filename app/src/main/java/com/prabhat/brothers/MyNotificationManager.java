package com.prabhat.brothers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by HP on 17-Apr-18.
 */

public class MyNotificationManager {

    private Context context;
    public static final int NOTIFICATION_ID = 01;
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id-1";

    public MyNotificationManager(Context context) {
        this.context = context;
    }

    public void showNotification(String from, String notification, Intent intent){

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

        Notification mNotification = builder
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("Brothers App")
                .setContentText(notification)
                .build();

        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManagerCompat notificationManager = (NotificationManagerCompat) NotificationManagerCompat.from(context);//context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mNotification);
    }
}
