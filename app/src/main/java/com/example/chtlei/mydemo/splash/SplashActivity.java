package com.example.chtlei.mydemo.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.chtlei.mydemo.MyDemoActivity;
import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 19-4-11.
 */

public class SplashActivity extends Activity {
    private TextView mTimeTV;
    private Button mSignBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        mTimeTV = findViewById(R.id.time_tv);
        mSignBtn = findViewById(R.id.sign_btn);

        final CountDownTimer timer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTV.setText("跳过 " + (millisUntilFinished/1000 + 1) + "s");
            }

            @Override
            public void onFinish() {
                startDemoActivity();
            }
        };
        timer.start();

        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.onFinish();
                    timer.cancel();
                }
            }
        });
    }

    private void startDemoActivity () {
        Intent intent = new Intent(SplashActivity.this, MyDemoActivity.class);
        startActivity(intent);
        finish();
    }
}
