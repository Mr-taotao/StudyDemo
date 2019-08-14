package com.example.chtlei.mydemo.jnitest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.chtlei.jnitestdemo.MainActivity;
import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 18-10-18.
 */

public class JNITestActivity extends Activity {
    private static final String TAG = "JNITestActivity";
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);

        bindView();

        initView();
    }

    private void initView() {
//        textView.setText(JNIUtils.stringFromJNI());
//        textView.setText(JNIUtils.encryptString("test jni"));
        textView.setText(MainActivity.stringFromJNI());
    }

    private void bindView() {
        textView = findViewById(R.id.jni_test);
    }
}
