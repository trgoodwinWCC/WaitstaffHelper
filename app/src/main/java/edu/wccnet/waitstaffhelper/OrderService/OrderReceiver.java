package edu.wccnet.waitstaffhelper.OrderService;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;


public class OrderReceiver extends BroadcastReceiver {

    private static final String TAG = OrderReceiver.class.getCanonicalName();

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences.Editor editObj = context.getSharedPreferences("MyPrefs",MODE_PRIVATE).edit();

        editObj.putBoolean("Saved",true);
        editObj.apply();

        Log.i(TAG,"Saved from notification");

        NotificationManager myManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myManager.cancel(intent.getIntExtra("ID",0));
    }
}