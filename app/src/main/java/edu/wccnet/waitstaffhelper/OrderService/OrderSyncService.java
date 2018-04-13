package edu.wccnet.waitstaffhelper.OrderService;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import edu.wccnet.waitstaffhelper.R;

import static java.util.concurrent.TimeUnit.MINUTES;


public class OrderSyncService extends IntentService {
    public OrderSyncService() {
        super("OrderSyncService" );
    }

    private static final String TAG = OrderSyncService.class.getCanonicalName();
    private static final String channelDefaultID = "default";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    protected void onHandleIntent(Intent intent) {

        final Runnable checkForUpdate = new Runnable() {
            public void run() {
                Log.i(TAG,"Ping");

                int orderNumber;
                String status;
                try {
                    URL url = new URL("https://api.jsonbin.io/b/5aca8021214f9a2b84c6e133/latest");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;
                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    JSONObject overViewJSON = new JSONObject(builder.toString());

                    Log.i(TAG,"getString('order') returns : "+overViewJSON.getString("order"));
                    orderNumber = overViewJSON.getInt("order");
                    Log.i(TAG,"getString('status') returns : "+overViewJSON.getString("status"));
                    status = overViewJSON.getString("status");

                    SharedPreferences readObj = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editObj = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE).edit();

                    if(readObj.getBoolean("Saved",false)==false) {
                        editObj.putInt("Order",orderNumber);
                        editObj.putString("Status",status);
                        editObj.putBoolean("Saved",false);
                        editObj.apply();
                        //notify here as a new order
                        Log.i(TAG,"New orderNumber, notify");
                        doHeadsUpNotification(getApplicationContext(),"New orderNumber");
                    }
                    else {
                        Log.i(TAG,"No new order");
                    }


                    //urlConnection.disconnect();
                    // for some reason it cannot reconnect if its closed. I wonder where to close it.
                } catch (IOException | JSONException e) {
                    Log.e(TAG,"Error from connection or json",e);
                    e.printStackTrace();
                }
            }
        };

        scheduler.scheduleWithFixedDelay(checkForUpdate, 1, 1, MINUTES);
    }

    public void doHeadsUpNotification(Context context,String messageString) {

        int id = 0;

        Intent orderIntent = new Intent(context,OrderReceiver.class);
        orderIntent.putExtra("ID",id);

        PendingIntent notifyOrderReceiver = PendingIntent.getBroadcast(context, 1, orderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_save, "SAVE DATA", notifyOrderReceiver).build();

        NotificationCompat.Builder notificationBuilder = createNotificationBuilder(context, "Order Service", messageString);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        notificationBuilder.addAction(replyAction);

        doNotification(context, notificationBuilder.build(), id);
    }

    private static NotificationCompat.Builder createNotificationBuilder(Context context, String title, String message) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.waitstaff_helper);
        NotificationCompat.Builder myNotification = new NotificationCompat.Builder(context, channelDefaultID);

        myNotification.setSmallIcon(R.drawable.dinner)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setColor(ContextCompat.getColor(context, R.color.backgroundColorSplashScreen))
                .setAutoCancel(true);

        return myNotification;
    }

    private static void doNotification(Context context, Notification notification, int id) {

        NotificationManager myManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myManager.notify(id, notification);
    }

}