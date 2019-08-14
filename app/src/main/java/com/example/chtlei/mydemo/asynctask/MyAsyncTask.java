package com.example.chtlei.mydemo.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;

import java.util.LinkedHashMap;

/**
 * Created by chtlei on 19-6-4.
 */

/**
 * Void    开始异步任务执行时传入的参数类型；
 * Integer 异步任务执行过程中，返回下载进度值的类型；
 * Object  异步任务执行完成后，返回的结果类型；
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, Object> {


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Void... voids) {

        LinkedHashMap<String,Integer> lm = new LinkedHashMap<>(1,0.8f,true);
        LruCache<String,Bitmap> lruCache = new LruCache<>(10);

        return null;
    }
}
