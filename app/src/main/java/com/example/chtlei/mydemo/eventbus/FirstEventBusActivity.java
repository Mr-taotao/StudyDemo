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

public class FirstEventBusActivity extends EventBusBaseActivity {

    @BindView(R.id.button_one)
    Button mButton;

    @BindView(R.id.textView_one)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LCT","FirstEventBusActivity onCreate");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eventbus_one;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

        mButton.setText("jumpToSecond");
        mTextView.setText("当前为第一个Activity");
    }

    @OnClick(R.id.button_one)
    public void clickButton() {
        Intent intent = new Intent(this, SecondEventBusActivity.class);
        startActivity(intent);
        EventBus.getDefault().post(new BaseEventMessage("来自FirstActivity的消息"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEventMessage message) {
        mTextView.setText(message.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("LCT","FirstEventBusActivity onDestroy");
    }
}
