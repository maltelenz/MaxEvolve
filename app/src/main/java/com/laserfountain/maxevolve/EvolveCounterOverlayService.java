package com.laserfountain.maxevolve;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class EvolveCounterOverlayService extends OverlayService {

    public static EvolveCounterOverlayService instance;

    private EvolveCounterOverlayView overlayView;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        overlayView = new EvolveCounterOverlayView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (overlayView != null) {
            overlayView.destroy();
        }

    }

    static public void stop() {
        if (instance != null) {
            instance.stopSelf();
        }
    }

    @Override
    protected void toggle(boolean booleanExtra) {
        Log.d("ECOverlayService", "toggling to: " + Boolean.toString(booleanExtra));
        if (overlayView != null) {
            overlayView.toggle(booleanExtra);
        }
    }

    private PendingIntent getShowIntent(Boolean shown) {
        Intent intent = new Intent(this, EvolveCounterOverlayService.class);
        intent.putExtra(getString(R.string.showExtra), !shown);

        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected Notification getNotification() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        PendingIntent pendingSettings = PendingIntent.getActivity(this, 42, settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Boolean shown;
        if (overlayView != null && overlayView.overlayIsShown()) {
            shown = true;
        } else {
            shown = false;
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.title_notification))
                        .setContentIntent(getShowIntent(shown))
                        .setColor(getResources().getColor(R.color.notification_color))
                        .setAutoCancel(false)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setOnlyAlertOnce(true)
                        .addAction(R.drawable.ic_settings_black_48dp, "Settings", pendingSettings);

        if (shown) {
            builder.setContentText(getString(R.string.message_hide));
        } else {
            builder.setContentText(getString(R.string.message_show));
        }

        return builder.build();
    }
}