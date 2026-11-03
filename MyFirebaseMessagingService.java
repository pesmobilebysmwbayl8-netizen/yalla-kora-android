package com.yallkora.com;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "yalla_kora_updates";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // FCM token is managed by Firebase. Do not publish it in client-side HTML.
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        String title = message.getNotification() != null
                ? message.getNotification().getTitle() : message.getData().get("title");
        String body = message.getNotification() != null
                ? message.getNotification().getBody() : message.getData().get("body");
        if (title == null || title.trim().isEmpty()) title = "يلا كورة";
        if (body == null || body.trim().isEmpty()) body = "لديك إشعار جديد";
        showNotification(title, body);
    }

    private void showNotification(String title, String body) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "إشعارات يلا كورة", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("الإشعارات الفورية من تطبيق يلا كورة");
            manager.createNotificationChannel(channel);
        }

        Intent launch = getPackageManager().getLaunchIntentForPackage(getPackageName());
        PendingIntent pending = null;
        if (launch != null) {
            launch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) flags |= PendingIntent.FLAG_IMMUTABLE;
            pending = PendingIntent.getActivity(this, 1001, launch, flags);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        if (pending != null) builder.setContentIntent(pending);
        manager.notify((int) (System.currentTimeMillis() & 0x7fffffff), builder.build());
    }
}
