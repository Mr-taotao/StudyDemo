package com.example.chtlei.mydemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chtlei.mydemo.aidl.Client;
import com.example.chtlei.mydemo.banner.BannerActivity;
import com.example.chtlei.mydemo.bannerads.ShowAdsViewActivity;
import com.example.chtlei.mydemo.broadcast.MyReceiver;
import com.example.chtlei.mydemo.broadcast.NoPermissionReceiver;
import com.example.chtlei.mydemo.contentprovider.MyContentUtils;
import com.example.chtlei.mydemo.deviceinfo.GetDeviceInfosActivity;
import com.example.chtlei.mydemo.filedownload.FileDownloadActivity;
import com.example.chtlei.mydemo.foreground.ForegroundTestService;
import com.example.chtlei.mydemo.foreground.ForegroundUtils;
import com.example.chtlei.mydemo.handler.MyHandlerTest;
import com.example.chtlei.mydemo.jnitest.JNITestActivity;
import com.example.chtlei.mydemo.jobscheduler.JobSchedulerActivity;
import com.example.chtlei.mydemo.jobscheduler.MyJobService;
import com.example.chtlei.mydemo.notification.NotificationMainActivity;
import com.example.chtlei.mydemo.photowall.PhotoWallActivity;
import com.example.chtlei.mydemo.proxy.MyInvokeHandler;
import com.example.chtlei.mydemo.proxy.ProxyInterface;
import com.example.chtlei.mydemo.proxy.ProxySubject;
import com.example.chtlei.mydemo.proxy.RealSubject;
import com.example.chtlei.mydemo.shortcut.ShortCutManager;
import com.example.chtlei.mydemo.sqllite.MyDBOperation;
import com.example.chtlei.mydemo.webview.WebViewActivity;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.BatchUpdateException;

public class MyDemoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MyDemoActivity";
    private static Context context;

    private DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_my_demo);

        //bindFragment();

        context = MyDemoActivity.this;

        initView();

        if (savedInstanceState != null) {
            Log.i(TAG,"savedInstance data is " + savedInstanceState.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        // store the data in the fragment
//        dataFragment.setData(loadData());

        Client.getInstance().unBindService(this);
    }

    private void initView() {
        findViewById(R.id.create_shortcut).setOnClickListener(this);
        findViewById(R.id.get_phone_info).setOnClickListener(this);
        findViewById(R.id.web_view).setOnClickListener(this);
        findViewById(R.id.send_broadcast).setOnClickListener(this);
        findViewById(R.id.jni_test_btn).setOnClickListener(this);
        findViewById(R.id.is_foreground_test_btn).setOnClickListener(this);
        findViewById(R.id.job_scheduler_test).setOnClickListener(this);
        findViewById(R.id.notification_btn).setOnClickListener(this);
        findViewById(R.id.photo_wall_btn).setOnClickListener(this);
        findViewById(R.id.fcm_btn).setOnClickListener(this);
        findViewById(R.id.banner_btn).setOnClickListener(this);
        findViewById(R.id.handler_btn).setOnClickListener(this);
        findViewById(R.id.contentprovider_btn).setOnClickListener(this);
        findViewById(R.id.sqllite_btn).setOnClickListener(this);
        findViewById(R.id.aidl_btn).setOnClickListener(this);
        findViewById(R.id.down_btn).setOnClickListener(this);
        findViewById(R.id.adsview_btn).setOnClickListener(v -> {
            ProxySubject proxySubject = new ProxySubject(new RealSubject());
            proxySubject.request();
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_shortcut:
                Log.i(TAG, "create_shortcut onclick");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ShortCutManager.addShortCut(this);
                }
                break;
            case R.id.get_phone_info:
                Log.i(TAG, "get_phone_info onclick");
                Intent intentPhone = new Intent(this, GetDeviceInfosActivity.class);
                startActivity(intentPhone);
                break;
            case R.id.web_view:
                Log.i(TAG, "web_view onclick");
                Intent intentWebView = new Intent(this, WebViewActivity.class);
                startActivity(intentWebView);
                break;
            case R.id.send_broadcast:
                Log.i(TAG, "send_broadcast onclick");
                Intent intentBroadcast = new Intent();
                intentBroadcast.setAction(NoPermissionReceiver.ACTION_FOREGROUND);
                intentBroadcast.setComponent(new ComponentName(context.getPackageName(),"com.example.chtlei.mydemo.broadcast.NoPermissionReceiver"));
                context.sendBroadcast(intentBroadcast);
//                intentBroadcast.setAction(MyReceiver.ACTION);
//                intentBroadcast.setComponent(new ComponentName(context.getPackageName(), "com.example.chtlei.mydemo.broadcast.MyReceiver"));
//                context.sendBroadcast(intentBroadcast, "com.example.chtlei.mydemo.broadcast");
                break;
            case R.id.jni_test_btn:
                Log.i(TAG, "jni_test_btn onclick");
                final Intent jniTset = new Intent(this, JNITestActivity.class);
                startActivity(jniTset);
                break;
            case R.id.is_foreground_test_btn:
                Log.i(TAG, "is_foreground_test_btn onclick");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, ForegroundTestService.class));
                } else {
                    context.startService(new Intent(context, ForegroundTestService.class));
                }
                if (ForegroundUtils.isAppForeground()) {
//                    Log.i(TAG,"isAppForeground");
                    Toast.makeText(context, "application is foreground", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(130000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            if (!ForegroundUtils.isAppForeground()) {
//                                //Toast.makeText(context, "application is background", Toast.LENGTH_SHORT).show();
//                                Log.i(TAG,"application is background");
//                                //context.startService(new Intent(context, ForegroundTestService.class));
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    ContextCompat.startForegroundService(context,new Intent(context, ForegroundTestService.class));
//                                } else {
//                                    context.startService(new Intent(context, ForegroundTestService.class));
//                                }
//                            }
                        }
                    }.start();
                }
                break;
            case R.id.job_scheduler_test:
                Log.i(TAG, "job_scheduler_test onclick");
////                Intent jobIntent = new Intent(this, JobSchedulerActivity.class);
////                startActivity(jobIntent);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                        ComponentName jobService = new ComponentName(getPackageName(), MyJobService.class.getName());
//                        PersistableBundle extras = new PersistableBundle();
//                        extras.putString("MyJobScheduler","startservice");
//
//                        JobInfo jobInfo = new JobInfo.Builder(100012, jobService) //任务Id等于100012
//                                .setMinimumLatency(5000)// 任务最少延迟时间为5s
//                                .setOverrideDeadline(60000)// 任务deadline，当到期没达到指定条件也会开始执行
//                                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)// 需要满足网络条件，默认值NETWORK_TYPE_NONE
//                                .setExtras(extras)
//                                //.setPeriodic(AlarmManager.INTERVAL_DAY) //循环执行，循环时长为一天（最小为15分钟）
//                                //.setRequiresCharging(true)// 需要满足充电状态
//                                //.setRequiresDeviceIdle(false)// 设备处于Idle(Doze)
//                                //.setPersisted(true) //设备重启后是否继续执行
//                                //.setBackoffCriteria(3000，JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略
//                                .build();
//                        scheduler.schedule(jobInfo);
//                    }
//                },130000);
                break;
            case R.id.notification_btn:
                Log.i(TAG, "notification_btn onclick");
                Intent intentNotification = new Intent(this, NotificationMainActivity.class);
//                intentNotification.putExtra("123",loadData());
                startActivity(intentNotification);
                break;
            case R.id.photo_wall_btn:
                Log.i(TAG, "photo_wall_btn onclick");
                Intent photoWallIntent = new Intent(this, PhotoWallActivity.class);
                startActivity(photoWallIntent);
                break;
            case R.id.fcm_btn:
//                Log.i(TAG, "fcm_btn onclick");
//                FirebaseInstanceId.getInstance().getInstanceId()
//                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                                if (!task.isSuccessful()) {
//                                    Log.w(TAG, "getInstanceId failed", task.getException());
//                                    return;
//                                }
//
//                                // Get new Instance ID token
//                                String token = task.getResult().getToken();
//
//                                // Log and toast
//                                String msg = getString(R.string.msg_token_fmt, token);
//                                Log.d(TAG, msg);
//                                Toast.makeText(MyDemoActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        });
                break;
            case R.id.banner_btn:
                Log.i(TAG, "banner_btn onclick");
                Intent bannerIntent = new Intent(this, BannerActivity.class);
                startActivity(bannerIntent);
                break;
            case R.id.handler_btn:
                Log.i(TAG, "handler_btn onclick");
                Intent handlerIntent = new Intent(this, MyHandlerTest.class);
                startActivity(handlerIntent);
                break;
            case R.id.contentprovider_btn:
                Log.i(TAG, "contentprovider_btn onclick");
                MyContentUtils.dealContentProvider(this);
                break;
            case R.id.sqllite_btn:
                new Thread(){
                    @Override
                    public void run() {
                        MyDBOperation myDBOperation = MyDBOperation.getInstance(MyDemoActivity.this);
                        myDBOperation.insertItem();
                        try {
                            myDBOperation.queryItem();

                            sleep(1000);
                            myDBOperation.updateItem();
                            myDBOperation.queryItem();

                            sleep(1000);
                            myDBOperation.deleteItem();
                            myDBOperation.queryItem();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
                break;
            case R.id.aidl_btn:
                Log.i(TAG, "aidl_btn onclick");
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Client.getInstance().bindService(MyDemoActivity.this);
                        return null;
                    }
                }.execute();

                break;
            case R.id.down_btn:
                Log.i(TAG, "down_btn onclick");
                Intent downloadIntent = new Intent(this, FileDownloadActivity.class);
                try {
                    startActivity(downloadIntent);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.adsview_btn:
                Log.i(TAG, "adsview_btn onclick");
//                Intent adsViewIntent = new Intent(this, ShowAdsViewActivity.class);
//                try {
//                    startActivity(adsViewIntent);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
                //静态代理
//                ProxySubject proxySubject = new ProxySubject(new RealSubject());
//                proxySubject.request();

                //动态代理
                try {
                    RealSubject realSubject = new RealSubject();
                    //创建一个InvocationHandler对象
                    InvocationHandler myInvokeHandler = new MyInvokeHandler(realSubject);

                    ProxyInterface p = (ProxyInterface) Proxy.newProxyInstance(RealSubject.class.getClassLoader(), RealSubject.class.getInterfaces(),
                            myInvokeHandler);

                    p.request();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArray("Strings",loadData());

        Parcel parcel = Parcel.obtain();
        parcel.writeBundle(outState);
        int size = parcel.dataSize();
        parcel.recycle();
        Log.i("LCT","Bundle size is " + size);
    }

    private String[] loadData() {
        String [] strings = new String[1024];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = "abcdefgh";
        }
        return strings;
    }
}
