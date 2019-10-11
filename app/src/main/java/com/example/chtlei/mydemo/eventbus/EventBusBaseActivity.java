package com.example.chtlei.mydemo.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class EventBusBaseActivity extends Activity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        EventBus.getDefault().register(this);

        ButterKnife.bind(this);
        
        initData();
        
        initView();
    }

    protected void initView() {
    }

    protected void initData() {
    }


    protected abstract @LayoutRes int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        if(unbinder != null && unbinder != Unbinder.EMPTY){
            unbinder.unbind();
            unbinder = null;
        }

    }
}
