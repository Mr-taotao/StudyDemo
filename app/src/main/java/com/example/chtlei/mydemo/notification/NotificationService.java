package com.example.chtlei.mydemo.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.chtlei.mydemo.BuildConfig;
import com.example.chtlei.mydemo.MyDemoApplication;
import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 18-10-25.
 */

public class NotificationService extends Service {
    private Context context;
    private RemoteViews NormalView;
    private RemoteViews expandView;
    private Notification notification;

    private ActionReceiver receiver = new ActionReceiver();

    private boolean isLove = false;
    private boolean isLyc = false;
    private boolean isPlaying = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        initNotification();

        //register action broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.NOTIFICATION_CLICK);
        filter.addAction(Constant.NOTIFICATION_LOVE);
        filter.addAction(Constant.NOTIFICATION_BACK);
        filter.addAction(Constant.NOTIFICATION_PAUSE);
        filter.addAction(Constant.NOTIFICATION_NEXT);
        filter.addAction(Constant.NOTIFICATION_LYC);
        filter.addAction(Constant.NOTIFICATION_CLOSE);
        registerReceiver(receiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    private void initNotification() {
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.setClass(context, ThirdActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(ThirdActivity.class);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) SystemClock.uptimeMillis(), PendingIntent.FLAG_UPDATE_CURRENT);


//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        Intent secondIntent = new Intent(context, ThirdActivity.class);
//        Intent[] intents = {intent, secondIntent};
//        PendingIntent pendingIntent = PendingIntent.getActivities(context, (int) SystemClock.uptimeMillis(), intents, PendingIntent.FLAG_UPDATE_CURRENT);

//        PendingIntent pendingIntent;
//        if (Util.isAppAlive(context, BuildConfig.APPLICATION_ID)) {
//            Intent intent = new Intent();
//            intent.setClass(context, ThirdActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        } else {
//            Intent intent = new Intent(context, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            Intent secondIntent = new Intent(context, ThirdActivity.class);
//            Intent[] intents = {intent, secondIntent};
//            pendingIntent = PendingIntent.getActivities(context, (int) SystemClock.uptimeMillis(), intents, PendingIntent.FLAG_UPDATE_CURRENT);
//        }


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_CLICK), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent loveIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_LOVE), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent backIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_BACK), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_PAUSE), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent nextIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_NEXT), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent lycIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_LYC), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent closeIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), new Intent(Constant.NOTIFICATION_CLOSE), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .setContentIntent(pendingIntent);

        NormalView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
        builder.setCustomContentView(NormalView);

        NormalView.setOnClickPendingIntent(R.id.iv_pause, pauseIntent);
        NormalView.setOnClickPendingIntent(R.id.iv_next, nextIntent);
        NormalView.setOnClickPendingIntent(R.id.iv_lyc, lycIntent);
        NormalView.setOnClickPendingIntent(R.id.iv_close, closeIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            expandView = new RemoteViews(context.getPackageName(), R.layout.notification_big_view);
            builder.setCustomBigContentView(expandView);
            builder.setPriority(Notification.PRIORITY_MAX);

            expandView.setOnClickPendingIntent(R.id.iv_love, loveIntent);
            expandView.setOnClickPendingIntent(R.id.iv_pause, pauseIntent);
            expandView.setOnClickPendingIntent(R.id.iv_back, backIntent);
            expandView.setOnClickPendingIntent(R.id.iv_next, nextIntent);
            expandView.setOnClickPendingIntent(R.id.iv_lyc, lycIntent);
            expandView.setOnClickPendingIntent(R.id.iv_close, closeIntent);
        }

        notification = builder.build();
        startForeground(Constant.ID_FOR_CUSTOM_VIEW, notification);
    }


    class ActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent data) {
            if (data == null || data.getAction() == null) {
                return;
            }
            switch (data.getAction()) {
                case Constant.NOTIFICATION_CLICK:
                    if (Util.isAppAlive(context, BuildConfig.APPLICATION_ID) && !MyDemoApplication.getInstance().isAllActivityFinished()) {
                        Intent intent = new Intent();
                        intent.setClass(context, NotificationThirdActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, NotificationMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Intent secondIntent = new Intent(context, NotificationThirdActivity.class);
                        Intent[] intents = {intent, secondIntent};
                        startActivities(intents);
                    }
                    break;
                case Constant.NOTIFICATION_LOVE:
                    NormalView.setImageViewResource(R.id.iv_love, isLove ? R.drawable.note_btn_love_white : R.drawable.note_btn_loved);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        expandView.setImageViewResource(R.id.iv_love, isLove ? R.drawable.note_btn_love_white : R.drawable.note_btn_loved);
                    }
                    isLove = !isLove;
                    startForeground(Constant.ID_FOR_CUSTOM_VIEW, notification);
                    break;
                case Constant.NOTIFICATION_BACK:
                    break;
                case Constant.NOTIFICATION_PAUSE:
                    NormalView.setImageViewResource(R.id.iv_pause, isPlaying ? R.drawable.note_btn_pause_white : R.drawable.note_btn_play_white);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        expandView.setImageViewResource(R.id.iv_pause, isPlaying ? R.drawable.note_btn_pause_white : R.drawable.note_btn_play_white);
                    }
                    isPlaying = !isPlaying;
                    startForeground(Constant.ID_FOR_CUSTOM_VIEW, notification);
                    break;
                case Constant.NOTIFICATION_NEXT:
                    break;
                case Constant.NOTIFICATION_LYC:
                    NormalView.setImageViewResource(R.id.iv_lyc, isLyc ? R.drawable.note_btn_lyc_white : R.drawable.note_btn_lyced);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        expandView.setImageViewResource(R.id.iv_lyc, isLyc ? R.drawable.note_btn_lyc_white : R.drawable.note_btn_lyced);
                    }
                    isLyc = !isLyc;
                    startForeground(Constant.ID_FOR_CUSTOM_VIEW, notification);
                    break;
                case Constant.NOTIFICATION_CLOSE:
                    stopSelf();
                    break;
            }

        }

    }
}
