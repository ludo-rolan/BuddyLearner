package com.example.buddylearner.ui.notifications;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.provider.Settings.System.getString;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.buddylearner.R;
import com.example.buddylearner.services.NotifyService;
import com.example.buddylearner.ui.base.HomeActivity;

import java.util.Calendar;

public class SendTutorRequest {

    public void send(Context context) {

//        // define the action of touching the notification
//        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, AlertDetails.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//        // add action button
//        Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
//        snoozeIntent.setAction(ACTION_SNOOZE);
//        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//        PendingIntent snoozePendingIntent =
//                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Hello World!")// configure a long notification - by default it's displayed on one line
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
//                        snoozePendingIntent);
////                .setAutoCancel(true);
//
//
//        // define the notification content
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
////                .setSmallIcon(R.drawable.notification_icon)
////                .setContentTitle("My notification")
////                .setContentText("Much longer text that cannot fit one line...")
////                // configure a long notification - by default it's displayed on one line
////                .setStyle(new NotificationCompat.BigTextStyle()
////                        .bigText("Much longer text that cannot fit one line..."))
////                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//
//        // display the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(notificationId, builder.build());



        // create a notificaiton - https://www.tutorialspoint.com/how-to-start-a-service-from-notification-in-android


    }

//    // create a channel and define the priority
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


}
