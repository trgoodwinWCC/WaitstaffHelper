package edu.wccnet.waitstaffhelper;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MINUTES;


public class OrderSyncService extends IntentService {
    public OrderSyncService() {
        super("OrderSyncService" );
    }

    private static final String channelDefaultID = "default";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beep");
                // code to do json stuff here
            }
        };

        scheduler.scheduleWithFixedDelay(beeper, 1, 1, MINUTES);

        //NotificationWorker.doBasicNotification(this, "Complete", "Finished with work!" ); do a headsupnotify instead
    }

    public static void doHeadsUpNotification(Context context) {
        Intent callWCCIntent = new Intent(Intent.ACTION_DIAL);
        Intent rightIntent = new Intent();
        callWCCIntent.setData(Uri.parse("tel:7349733300"));

        PendingIntent callWCC = PendingIntent.getActivity(context, 1,
                callWCCIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(android.R.drawable.sym_action_call, "CALL WCC", callWCC).build();

        NotificationCompat.Builder notificationBuilder = createNotificationBuilder(context, "Phone a friend", "This is a heads up notification, I suggest calling WCC");
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        notificationBuilder.addAction(replyAction);

        doNotification(context, notificationBuilder.build(), 0);
    }

    private static NotificationCompat.Builder createNotificationBuilder(Context context, String title, String message) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.waitstaff_helper);
        NotificationCompat.Builder myNotification = new NotificationCompat.Builder(context, channelDefaultID);

        // You'll need to update the minimum SDK version to see this working properly, you'll also
        // need to run in an emulator that supports at least API level 26
        //CharSequence channelName = "Default Channel";
        //int importance = NotificationManager.IMPORTANCE_LOW;
        //NotificationChannel notificationChannel = new NotificationChannel(channelDefaultID, channelName, importance);
        //notificationChannel.setLightColor(Color.RED);

        myNotification.setSmallIcon(R.drawable.waitstaff_helper)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(largeIcon)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setAutoCancel(true);

        return myNotification;
    }

    private static void doNotification(Context context, Notification notification, int id) {

        NotificationManager myManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        myManager.notify(id, notification);
    }

/*    class BeeperControl {
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        public void beepForAnHour() {

            final Runnable beeper = new Runnable() {
                public void run() {
                    System.out.println("beep");
                    // code to do stuff here
                }
            };

            scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS); // put the runnable code plus time here.

            *//*
            the following code is the same as above but is cancelled when the new schedule executes.

            final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);

            scheduler.schedule(new Runnable() {
                public void run() {
                    beeperHandle.cancel(true);
                }
            }, 60 * 60, SECONDS);
            *//*

        }
    }*/


}