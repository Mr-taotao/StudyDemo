package com.example.chtlei.mydemo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.chtlei.mydemo.R;
import com.example.chtlei.mydemo.eventbus.event.BaseEventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondEventBusActivity extends EventBusBaseActivity {
    @BindView(R.id.button_two)
    Button mButton;

    @BindView(R.id.textView_two)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LCT","SecondEventBusActivity onCreate");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eventbus_two;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

        mButton.setText("jumpToThree");
        mTextView.setText("当前为第二个Activity");
    }

    @Nullable
    @OnClick(R.id.button_two)
    public void clickButton() {
        Intent intent = new Intent(this, ThirdEventBusActivity.class);
        startActivity(intent);
        EventBus.getDefault().post(new BaseEventMessage("来自SecondActivity的消息"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEventMessage message) {
        mTextView.setText(message.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("LCT","SecondEventBusActivity onDestroy");
    }
}
