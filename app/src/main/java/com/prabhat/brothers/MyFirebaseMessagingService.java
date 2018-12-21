package com.prabhat.brothers;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by HP on 17-Apr-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "BrothersAppNotification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, remoteMessage.getFrom());
        Log.d(TAG, remoteMessage.getNotification().getBody());

        String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();
        String notification = remoteMessage.getNotification().getBody();

        notifyUser(from, notification);

    }

    public void notifyUser(String from, String notification){

        MyNotificationManager notificationManager = new MyNotificationManager(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), homeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("from", from);
        intent.putExtra("notificationText", notification);
        notificationManager.showNotification(from, notification, intent);

    }
}
