package com.cleverTapTest.clevertapandroid;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class PushNotification extends FirebaseMessagingService {
    private static final String TAG = PushNotification.class.getSimpleName();
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        super.onNewToken(token);
    }
}
