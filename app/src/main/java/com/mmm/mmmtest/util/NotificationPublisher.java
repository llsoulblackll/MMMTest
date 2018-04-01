package com.mmm.mmmtest.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class NotificationPublisher extends BroadcastReceiver{

    public static final String NOTIFICATION_KEY = "notification";
    public static final String NOTIFICATION_ID_KEY = "notification_id";
    public static final String NOTIFICATION_LOOP_KEY = "notification_loop";

    private Notification notification;
    private int notificationId;

    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        notification = intent.getParcelableExtra(NOTIFICATION_KEY);
        notificationId = intent.getIntExtra(NOTIFICATION_ID_KEY, 0);

        if(notificationManager == null)
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(notification != null)
            notificationManager.notify(notificationId, notification);

    }
}
