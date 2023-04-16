package com.example.buddylearner.services;

import static androidx.core.content.ContextCompat.getSystemService;

import static com.example.buddylearner.ui.elements.FollowTopicCategoryModalBottomSheet.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.buddylearner.R;
import com.example.buddylearner.ui.notification.NotificationActivity;

//
public abstract class NotifyService extends Service {

    protected int REQUEST_CODE, NOTIFICATION_ID = 3000;
    protected String CHANNEL_ID = "ForegroundService_ID";
    protected String CHANNEL_NAME = "ForegroundService Channel";
    protected final IBinder mBinder = new LocalBinder();

    protected NotificationManager mNotificationManager;
    protected NotificationCompat.Builder mNotificationBuilder;

    public class LocalBinder extends Binder {
        public NotifyService getService() {
            return NotifyService.this;
        }
    }

    protected abstract Notification serviceNotification();

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        startForeground(NOTIFICATION_ID, serviceNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    protected Notification createNotification(String title, String message, int smallIcon, int bigIcon, Class<?> intentClass) {

        mNotificationBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        if (smallIcon != 0) {
            mNotificationBuilder.setSmallIcon(smallIcon);
        }

        if (bigIcon != 0) {
            mNotificationBuilder.setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), bigIcon), 128, 128, true));
        }

        if (intentClass != null) {
            Intent notificationIntent = new Intent(this, intentClass);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mNotificationBuilder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            channel.setShowBadge(false);
            channel.setImportance(NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
        }

        return mNotificationBuilder.build();
    }



    private Notification createNotification(String message, Context context) {

        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_main);
        notificationLayout.setTextViewText(R.id.txtTitle, message);

        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);

        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        // PendingIntent.FLAG_IMMUTABLE - 0 previously
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 125, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Bitmap payableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_account_circle_24);

        mBuilder.setContentTitle("My Service")
                .setContentText(message)
                .setPriority(Notification.PRIORITY_HIGH)
                .setLargeIcon(payableLogo)
                .setSmallIcon(R.drawable.baseline_account_circle_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)

                .setCustomBigContentView(notificationLayout);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = CHANNEL_ID;
            NotificationChannel channel = new NotificationChannel(channelId, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        return mBuilder.build();
    }



    private void showNotification(String message, Context context) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, createNotification(message, context));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public static void start(Context context, Class<? extends NotifyService> serviceClass) {
        ContextCompat.startForegroundService(context, new Intent(context, serviceClass));
    }

    public static void stop(Context context, Class<? extends NotifyService> serviceClass) {
        context.stopService(new Intent(context, serviceClass));
    }
}
