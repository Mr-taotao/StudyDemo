package com.example.chtlei.mydemo.webview;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by chtlei on 18-10-15.
 */

public class MyWebChromeClient extends WebChromeClient{
    private static final String TAG = "MyWebChromeClient";
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.i(TAG,"newProgress is :" + newProgress);
        if (newProgress > 0 && newProgress <= 100) {
            WebViewActivity.progressBar.setProgress(newProgress);
        }else {
            WebViewActivity.progressBar.setProgress(100);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        super.onReceivedTouchIconUrl(view, url, precomposed);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }
}
