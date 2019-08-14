package com.example.chtlei.mydemo.bannerads;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chtlei.mydemo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by chtlei on 19-7-5.
 */

public class ShowAdsViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_ads_view);

        initView();
    }

    private void initView() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i("LCT","onAdFailedToLoad " + i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("LCT","onAdLoaded");
            }
        });
        mAdView.loadAd(adRequest);
    }
}
