package com.example.chtlei.mydemo.webview;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static com.example.chtlei.mydemo.webview.WebViewActivity.code;

/**
 * Created by chtlei on 18-10-10.
 */

public class MyWebViewClient extends WebViewClient{
    private static final String TAG = "MyWebViewClient";

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i(TAG,"onPageStarted");
        super.onPageStarted(view, url, favicon);
        WebViewActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.i(TAG,"shouldInterceptRequest");
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i(TAG,"shouldOverrideUrlLoading");
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i(TAG,"onPageFinished");
        super.onPageFinished(view, url);
//        view.addJavascriptInterface(new AndroidToJs(), "Android");
        WebViewActivity.progressBar.setVisibility(View.INVISIBLE);
        view.loadUrl(WebViewActivity.JsCode);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        Log.i(TAG,"onReceivedError");
        super.onReceivedError(view, request, error);
    }
}
