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

public class ThirdEventBusActivity extends EventBusBaseActivity {
    @BindView(R.id.button_three)
    Button mButton;

    @BindView(R.id.textView_three)
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("LCT","ThirdEventBusActivity onCreate");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eventbus_three;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();

        mButton.setText("jumpToFirst");
        mTextView.setText("当前为第三个Activity");
    }

    @Nullable
    @OnClick(R.id.button_three)
    public void clickButton() {
        Intent intent = new Intent(this, FirstEventBusActivity.class);
        startActivity(intent);
        EventBus.getDefault().post(new BaseEventMessage("来自ThirdActivity的消息"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEventMessage message) {
        mTextView.setText(message.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("LCT","ThirdEventBusActivity onDestroy");
    }
}
