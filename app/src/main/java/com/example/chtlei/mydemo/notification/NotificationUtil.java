package com.example.chtlei.mydemo.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import com.example.chtlei.mydemo.R;

import java.util.List;

/**
 * Created by chtlei on 18-10-25.
 */

public class NotificationUtil {
    /**
     *
     * @param context
     * @param id 通知渠道id
     * @param name 通知渠道名称
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, String id, String name){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.createNotificationChannel(channel);
    }

    /**
     * show normal notification
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     * @param isOnly       only show one notification
     */
    public static void normal(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel, boolean isOnly) {
        String title = "This is normal title";
        String text = "This is normal message";
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }

        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(isOnly ? Constant.ID_FOR_NORMAL : (int) System.currentTimeMillis(), builder.build());
    }

    /**
     * Show normal notification with action (reply, make as read, delete and so on.)
     * You can do something directly in the notification and don't must open the app.
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     */
    public static void normalWithAction(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel) {
        String title = "This is normal action title";
        String text = "This is normal action message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }

        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);


        RemoteInput input = new RemoteInput.Builder(Constant.KEY_TEXT_REPLY).setLabel("请输入内容").build();

        Intent reply = new Intent();
        reply.setAction(Constant.REPLY);
        reply.setClass(context, NotificationReceiver.class);
        PendingIntent pendingReply = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), reply, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent maskAsRead = new Intent();
        maskAsRead.setAction(Constant.MAKE_AS_READ);
        maskAsRead.setClass(context, NotificationReceiver.class);
        PendingIntent pendingMaskAsRead = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), maskAsRead, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent delete = new Intent();
        delete.setAction(Constant.DELETE);
        delete.setClass(context, NotificationReceiver.class);
        PendingIntent pendingDelete = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), delete, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action action = new NotificationCompat.Action.Builder(0, "reply", pendingReply)
                .addRemoteInput(input)
                .setAllowGeneratedReplies(true)
                .build();

        builder.addAction(action);
        builder.addAction(0, "mask as read", pendingMaskAsRead);
        builder.addAction(0, "delete", pendingDelete);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constant.ID_FOR_NORMAL_ACTION, builder.build());
    }


    /**
     * Show big text notification
     * You can show more detail in the notification.
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     * @param isOnly       only show one notification
     */
    public static void bigText(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel, boolean isOnly) {
        String title = "This is big text title";
        String text = "A notification is a message you can display " +
                "to the user outside of your application's normal UI. " +
                "When you tell the system to issue a notification, " +
                "it first appears as an icon in the notification area. ";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(title);
        style.bigText(text);
        style.setSummaryText(context.getString(R.string.app_name));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(style)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }

        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(isOnly ? Constant.ID_FOR_BIG_TEXT : (int) System.currentTimeMillis(), builder.build());
    }


    /**
     * Show inbox notification
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     * @param isOnly       only show one notification
     */
    public static void inbox(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel, boolean isOnly) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.addLine("This is first inbox message");
        style.addLine("This is second inbox message");
        style.addLine("This is third inbox message");
        style.addLine("This is fourth inbox message");
        style.addLine("This is fifth inbox message");
        style.addLine("This is sixth inbox message");
        style.addLine("This is seventh inbox message");
        style.setBigContentTitle("This is inbox title");
        style.setSummaryText(context.getString(R.string.app_name));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle("This is inbox title")
                .setContentText("This is inbox message")
                .setCategory(NotificationCompat.CATEGORY_EMAIL)
                .setStyle(style)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(isOnly ? Constant.ID_FOR_INBOX : (int) System.currentTimeMillis(), builder.build());
    }

    /**
     * Show group notification
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     */
    public static void group(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel) {
        String title = "This is inbox title";
        String text = "This is inbox message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setGroup("smile")
                .setGroupSummary(true)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }

        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constant.ID_FOR_GROUP, builder.build());

        for (int i = 1; i <= 10; i++) {
            NotificationCompat.Builder noti = new NotificationCompat.Builder(context,"chtlei");
            noti.setSmallIcon(R.drawable.cry)
                    .setContentTitle("Smile" + i)
                    .setContentText("This is " + i + " group message")
                    .setGroup("smile")
                    .setLargeIcon(largeIcon);
            notificationManager.notify(Constant.ID_FOR_GROUP + i, noti.build());
        }
    }

    /**
     * Show big picture notification
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     * @param isOnly       only show one notification
     */
    public static void bigPicture(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel, boolean isOnly) {
        String title = "This is big picture title";
        String text = "This is big picture message";

//        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.google));
        style.setBigContentTitle(title);
        style.setSummaryText(text);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");

//        builder.setLargeIcon(largeIcon)
        builder.setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(style)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);
        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(isOnly ? Constant.ID_FOR_BIG_PICTURE : (int) System.currentTimeMillis(), builder.build());
    }

    /**
     * Show big messaging notification (group chat)
     *
     * @param context      context
     * @param isSound      Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock   show when mobile locks screen
     * @param isHeads      heads up dialog
     * @param isAutoCancel cancel notification while click
     * @param list         message list
     */
    public static void messaging(Context context, boolean isSound, boolean isShowLock, boolean isHeads, boolean isAutoCancel,
                                 List<Message> list) {
        String title = "This is messaging title";
        String text = "This is messaging message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle("Smile");

        for (Message message : list) {
            style.addMessage(message.getText(), message.getTime(), message.getSender());
        }

        style.setConversationTitle(title);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(style)
                .setAutoCancel(isAutoCancel)
                .setContentIntent(pendingIntent);

        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        builder.setPriority(isHeads ? NotificationCompat.PRIORITY_MAX : NotificationCompat.PRIORITY_DEFAULT);


        RemoteInput input = new RemoteInput.Builder(Constant.KEY_TEXT_REPLY).setLabel("reply").build();

        Intent reply = new Intent();
        reply.setAction(Constant.REPLY_MESSAGING);
        reply.setClass(context, NotificationReceiver.class);
        PendingIntent pendingReply = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), reply, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action action = new NotificationCompat.Action.Builder(0, "reply", pendingReply)
                .addRemoteInput(input)
                .setAllowGeneratedReplies(true)
                .build();

        builder.addAction(action);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constant.ID_FOR_MESSAGING, builder.build());
    }

    /**
     * Show big messaging update notification (group chat)
     *
     * @param context context
     * @param list    message list
     */
    public static void messagingUpdate(Context context, List<Message> list) {
        String title = "This is messaging title";
        String text = "This is messaging message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle("Smile");

        for (Message message : list) {
            style.addMessage(message.getText(), message.getTime(), message.getSender());
        }

        style.setConversationTitle(title);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(style)
                .setContentIntent(pendingIntent);

        RemoteInput input = new RemoteInput.Builder(Constant.KEY_TEXT_REPLY).setLabel("reply").build();

        Intent reply = new Intent();
        reply.setAction(Constant.REPLY_MESSAGING);
        reply.setClass(context, NotificationReceiver.class);
        PendingIntent pendingReply = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), reply, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action action = new NotificationCompat.Action.Builder(0, "reply", pendingReply)
                .addRemoteInput(input)
                .setAllowGeneratedReplies(true)
                .build();

        builder.addAction(action);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constant.ID_FOR_MESSAGING, builder.build());
    }


    /**
     * Show big media notification (group chat)
     *
     * @param context    context
     * @param isSound    Set the sound to play.  if no, it will play on the default stream.
     * @param isShowLock show when mobile locks screen
     */
    public static void media(Context context, boolean isSound, boolean isShowLock) {
        String title = "This is media title";
        String text = "This is media message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.google);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, NotificationMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        Notification.MediaStyle style = new Notification.MediaStyle();
//        style.setShowCancelButton(true);
//        style.setCancelButtonIntent(pendingIntent);
//        style.setShowActionsInCompactView(0, 1);
//
//        style.setMediaSession(new MediaSessionCompat(context, "MediaSession",
//                new ComponentName(context, Intent.ACTION_MEDIA_BUTTON), null).getSessionToken());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"chtlei");
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.cry)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setCategory(NotificationCompat.CATEGORY_TRANSPORT)
//                .setStyle(style)
                .setOngoing(true)
                .setContentIntent(pendingIntent);


        if (isSound) {
            builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.message));
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL);
        }
        if (isShowLock) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }


        Intent back = new Intent();
        back.setAction(Constant.BACK);
        back.setClass(context, NotificationReceiver.class);
        PendingIntent pendingBack = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), back, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent next = new Intent();
        next.setAction(Constant.NEXT);
        next.setClass(context, NotificationReceiver.class);
        PendingIntent pendingNext = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), next, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pause = new Intent();
        pause.setAction(Constant.PAUSE);
        pause.setClass(context, NotificationReceiver.class);
        PendingIntent pendingPause = PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), pause, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.back, "", pendingBack);
        builder.addAction(R.drawable.pause, "", pendingPause);
        builder.addAction(R.drawable.next, "", pendingNext);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constant.ID_FOR_MEDIA, builder.build());

    }

    /**
     * cancel all notifications
     *
     * @param context context
     */
    public static void cancel(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        context.stopService(new Intent(context, NotificationService.class));
    }

    /**
     * cancel target id notifications
     *
     * @param context context
     */
    public static void cancel(Context context, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }
}
