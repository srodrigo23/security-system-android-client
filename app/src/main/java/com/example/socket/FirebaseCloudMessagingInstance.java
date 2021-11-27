package com.example.socket;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;

public class FirebaseCloudMessagingInstance extends FirebaseMessagingService {

    public FirebaseCloudMessagingInstance(){

    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("Tag", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Tag", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("Tag", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
