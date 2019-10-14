package com.example.chtlei.mydemo.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import java.io.IOException;

public class BitmapBuilder {

    public static void calculateOptionsById(@NonNull Resources res,@NonNull BitmapFactory.Options options, int imgId) {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, imgId, options);
    }

    public static int calculateInSamplesizeByOptions(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize   = 1;
        int originalWidth  = options.outWidth;
        int originalHeight = options.outHeight;
        if (originalHeight > reqHeight || originalWidth > reqWidth) {
            int heightRatio = originalHeight / reqHeight;
            int widthRatio  = originalWidth  / reqWidth;
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        options.inJustDecodeBounds = false;
        return inSampleSize;
    }

    public static Bitmap decodeBitmapById (@NonNull Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        calculateOptionsById(res, options, resId);
        options.inSampleSize = calculateInSamplesizeByOptions(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }

    public static Bitmap getBitmapByAssetsName(Context context, String name){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;

        try {
            return BitmapFactory.decodeStream(context.getAssets().open(name),new Rect(),options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapByAssetsNameRGB(Context context, String name){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inMutable = true;

        try {
            return BitmapFactory.decodeStream(context.getAssets().open(name),new Rect(),options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}