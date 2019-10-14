package com.example.chtlei.mydemo.rxjava;

import android.widget.Button;
import android.widget.TextView;

import com.example.chtlei.mydemo.R;
import com.example.chtlei.mydemo.eventbus.EventBusBaseActivity;
import com.example.chtlei.mydemo.eventbus.event.RxJavaEventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RxJavaActivity extends EventBusBaseActivity {
    @BindView(R.id.button_one)
    Button mButton;

    @BindView(R.id.textView_one)
    TextView mTextView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void initView() {
        super.initView();

        mButton.setText("Start Test");

        mTextView.setText("RxJava");
    }

    @OnClick(R.id.button_one)
    public void onClick () {
//        RxJavaUtils.getInstance().testCreate();
        RxJavaUtils.getInstance().testFlatMap();
    }

    @Subscribe
    public void onEvent(RxJavaEventMessage message) {
        mTextView.setText(message.getMessage());
    }
}
