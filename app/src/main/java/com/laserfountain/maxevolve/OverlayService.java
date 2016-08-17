package com.laserfountain.maxevolve;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class OverlayService extends Service {

    protected boolean foreground = false;
    private boolean firstStart;

    private final int NOTIFICATION_ID = 1;

    public void moveToForeground() {
        this.foreground = true;
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, getNotification());
        super.startForeground(NOTIFICATION_ID, getNotification());
    }

    public void moveToBackground() {
        foreground = false;
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(NOTIFICATION_ID, getNotification());
        super.stopForeground(false);
    }

    @Override
    public void onCreate() {
        firstStart = true;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("OverlayService", "onStartCommand");
        if (firstStart) {
            toggle(true);
        } else if (intent != null && intent.hasExtra(getString(R.string.showExtra))) {
            Log.d("OverlayService", "Intent is not null");
            toggle(intent.getBooleanExtra(getString(R.string.showExtra), false));
        }
        firstStart = false;
        return START_STICKY;
    }

    protected Notification getNotification() {
        // Overridden in EvolveCounterOverlayService
        return null;
    }

    protected void toggle(boolean booleanExtra) {
        // Overridden in EvolveCounterOverlayService
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
