package com.example.chtlei.mydemo.deviceinfo;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by chtlei on 18-10-9.
 */

public class DeviceInfoUtil {
    public static final int INTERNAL_STORAGE = 0;
    public static final int EXTERNAL_STORAGE = 1;
    /**
     * 生产商家
     *
     * @return
     */
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取产品名称
     *
     * @return
     */
    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获得手机品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获得手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机主板名
     *
     * @return
     */
    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 获取设备名
     *
     * @return
     */
    public static String getDeviceDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取手机硬件序列号
     */
    public static String getDeviceSerial() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机Android 系统SDK
     */
    public static int getDeviceSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获得Android 版本
     */
    public static String getDeviceAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机系统语言
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取手机运营商
     */
    public static String getDeviceSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getSimOperatorName();
    }

    /**
     * 获取设备宽度（px）
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备唯一标识号
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "unknown";
        } else {
            String deviceId = tm.getDeviceId();
            if (deviceId == null) {
                return "unknown";
            } else {
                return deviceId;
            }
        }
    }

    /**
     * 得到本机手机号码,未安装SIM卡或者SIM卡中未写入手机号，都会获取不到
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "unknown";
        } else {
            return tm.getLine1Number();
        }
    }

    /**
     * 得到屏幕信息
     * getScreenDisplayMetrics().heightPixels 屏幕高
     * getScreenDisplayMetrics().widthPixels 屏幕宽
     * @return
     */
    public static DisplayMetrics getScreenDisplayMetrics(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = manager.getDefaultDisplay();
        display.getMetrics(displayMetrics);

        return displayMetrics;

    }

    /**
     * 屏幕分辨率
     *
     * @param context
     * @return
     */
    public static float getDeviceDip(Context context) {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 获取手机存储ROM信息
     * type:用于区分内置存储和外置存储
     * 内置SD卡：INTERNAL_STORAGE = 0;
     * 外置SD卡：EXTERNAL_STORAGE = 1;
     */
    public static String getDeviceStorageInfo(Context context, int type) {
        String path = getStoragePath(context,type);

        if (isSDCardMount() == false || TextUtils.isEmpty(path) || path == null) {
            return "无外置SD卡";
        }

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
        String storageInfo;

        long blockCount = statFs.getBlockCountLong();
        long blockSize = statFs.getBlockSizeLong();
        long totalSize = blockCount * blockSize;

        long availableBlocks = statFs.getAvailableBlocksLong();
        long availableSpace  = availableBlocks * blockSize;

        storageInfo = "可用/总共："
                + Formatter.formatFileSize(context,availableSpace) + "/"
                + Formatter.formatFileSize(context,totalSize);

        return storageInfo;
    }

    /**
     * 获取手机RAM信息
     */
    public static String getDeviceRAMInfo(Context context){
        long totalSize = 0;
        long availableSpace = 0;
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        totalSize = memoryInfo.totalMem;
        availableSpace = memoryInfo.availMem;

        return "可用/总共："
                + Formatter.formatFileSize(context,availableSpace) + "/"
                + Formatter.formatFileSize(context,totalSize);
    }

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardMount() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机存储路径
     * 使用反射方法获取
     */
    public static String getStoragePath(Context context, int type) {
        StorageManager storageManager = (StorageManager)context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getPathsMethod = storageManager.getClass().getMethod("getVolumePaths");
            String[] paths = (String[]) getPathsMethod.invoke(storageManager);

            switch(type){
                case INTERNAL_STORAGE:
                    return paths[type];
                case EXTERNAL_STORAGE:
                    if (paths.length > 1) {
                        return paths[type];
                    } else {
                        return null;
                    }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
