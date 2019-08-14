package com.example.chtlei.mydemo.shortcut;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.widget.Toast;

import com.example.chtlei.mydemo.MyDemoActivity;
import com.example.chtlei.mydemo.R;

import java.util.List;

/**
 * Created by chtlei on 18-10-8.
 */

public class ShortCutManager {
    /**
     * android O 添加桌面快捷方式
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addShortCut(Context context) {
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);

        if (isShortCutExist(context, shortcutManager, "The only id")) {
            return;
        }
        if (shortcutManager.isRequestPinShortcutSupported()) {
            Intent shortcutInfoIntent = new Intent(context, MyDemoActivity.class);
            shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错

            ShortcutInfo info = new ShortcutInfo.Builder(context, "The only id")
                    .setIcon(Icon.createWithResource(context, R.mipmap.ic_shortcut))
                    .setShortLabel("Short Label")
                    .setIntent(shortcutInfoIntent)
                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调
            Intent intent = new Intent(context,ShortCutReceiver.class);
            intent.setAction("CREATE_SHORTCUT");
            intent.putExtra("shortcut_name","Short Label");

            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isShortCutExist(Context c , ShortcutManager shortcutManager, String id){
        if (shortcutManager != null && id != null) {
            List infos = shortcutManager.getPinnedShortcuts();
            for (int i = 0; i < infos.size(); i++) {
                ShortcutInfo info = (ShortcutInfo) infos.get(i);
                if (info.getId().equals(id)) {
                    Toast.makeText(c,"快捷方式已存在",Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 使用ShortcutManagerCompat添加桌面快捷方式
     *
     * @param context
     */
    public static void addShortCutCompact(Context context) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            Intent shortcutInfoIntent = new Intent(context, MyDemoActivity.class);
            shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错

//            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, "The only id")
//                    .setIcon(R.mipmap.ic_shortcut)
//                    .setShortLabel("Short Label")
//                    .setIntent(shortcutInfoIntent)
//                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调
//            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, ShortCutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
//            ShortcutManagerCompat.requestPinShortcut(context, info, shortcutCallbackIntent.getIntentSender());
        }
    }

    /**
     * Android 7.1及以下 添加桌面
     *
     * @param context
     */
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    public static void addShortcutBelowAndroidN(Context context) {
        Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);

        // 不允许重复创建，不是根据快捷方式的名字判断重复的
        addShortcutIntent.putExtra("duplicate", false);

        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Shortcut Name");

        //图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.mipmap.ic_shortcut));

        // 设置关联程序
        Intent launcherIntent = new Intent();
        launcherIntent.setClass(context, MyDemoActivity.class);
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        context.sendBroadcast(addShortcutIntent);
    }
}
