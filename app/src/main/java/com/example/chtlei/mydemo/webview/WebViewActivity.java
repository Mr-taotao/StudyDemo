package com.example.chtlei.mydemo.webview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 18-10-10.
 */

public class WebViewActivity extends Activity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;
    public static ProgressBar progressBar;
    public static String code;
    public static String JsCode = "javascript:var requestUrl='https://opsen.dolphin-browser.com/ipo/api/ads/web-embedded-ad';var thisHostname=window.location.hostname;var screenWidth=window.screen.width;var screenHeight=window.screen.height;var defaultData={top_banner:'https://static.thefifthera.com/h5/wapSdk/asset/images/open-font.png',top_banner_w:'320',top_banner_h:'100',top_url:'https://www.baidu.com',bottom_banner:'https://static.thefifthera.com/h5/wapSdk/asset/images/open-font.png',bottom_banner_w:'320',bottom_banner_h:'100',bottom_url:'https://www.baidu.com'};addAds(defaultData);function addAds(data){var newTopNode=document.createElement('div');var newBotNode=document.createElement('div');if(data.top_banner){newTopNode.setAttribute('style','width:'+screenWidth+'px;height:'+screenWidth*(data.top_banner_h/data.top_banner_w)+'px;background: url('+data.top_banner+') center/cover no-repeat;z-index:9999999;display:inline-block;position:relative;left:0;top:0;');newTopNode.onclick=function(event){event.preventDefault();console.log(666);var result=window.Android.back('a')};document.body.prepend(newTopNode)}if(data.bottom_banner){newBotNode.setAttribute('style','width:'+screenWidth+'px;height:'+screenWidth*(data.bottom_banner_h/data.bottom_banner_h)+'px;background: url('+data.bottom_banner+') center/cover no-repeat;z-index:9999999;display:inline-block;position:fixed;left:0;bottom:0;');newBotNode.setAttribute('target','_blank');newBotNode.setAttribute('href',data.bottom_url);document.documentElement.append(newBotNode)}window.onscroll=function(){if(window.scrollY>=10){newTopNode.style.position='fixed'}else{newTopNode.style.position='relative'}if(window.scrollY+screenHeight+200>=document.body.offsetHeight){newBotNode.style.position='relative'}}};";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        bindView();

        initData();

        initWebView();
    }

    private void bindView() {
        progressBar = findViewById(R.id.my_web_progress);
        webView = findViewById(R.id.my_web_view);
    }

    private void initData() {
        code = NightMode.getCss(this);
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持js
        webSettings.setJavaScriptEnabled(true);

        String userAgent = webSettings.getUserAgentString();
        Log.i(TAG,"userAgent is :" + userAgent);

        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //自动缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        webView.addJavascriptInterface(new AndroidToJs(), "Android");

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl("https://www.jianshu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
