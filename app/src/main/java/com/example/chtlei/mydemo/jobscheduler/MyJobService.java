package com.example.chtlei.mydemo.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.chtlei.mydemo.broadcast.NoPermissionReceiver;

import static com.example.chtlei.mydemo.jobscheduler.JobSchedulerActivity.MESSENGER_INTENT_KEY;
import static com.example.chtlei.mydemo.jobscheduler.JobSchedulerActivity.MSG_COLOR_START;
import static com.example.chtlei.mydemo.jobscheduler.JobSchedulerActivity.MSG_COLOR_STOP;
import static com.example.chtlei.mydemo.jobscheduler.JobSchedulerActivity.WORK_DURATION_KEY;

/**
 * Created by chtlei on 18-10-23.
 */

public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();

    private Messenger mActivityMessenger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    // 当应用程序的MainActivity被创建时，它启动这个服务。
    // 这是为了使活动和此服务可以来回通信。 请参见“setUiCallback（）”
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).

        // 该服务做的工作只是等待一定的持续时间并完成作业（在另一个线程上）。
//        sendMessage(MSG_COLOR_START, params.getJobId());
//        // 当然这里可以处理其他的一些任务
//        // TODO something else
        String action = params.getExtras().getString("MyJobScheduler");
        if (action.equals("startservice")) {
            Intent intentBroadcast = new Intent();
            intentBroadcast.setAction(NoPermissionReceiver.ACTION_FOREGROUND);
            intentBroadcast.setComponent(new ComponentName(getPackageName(),"com.example.chtlei.mydemo.broadcast.NoPermissionReceiver"));
            sendBroadcast(intentBroadcast);
        }
//
//        // 获取在activity里边设置的每个任务的周期，其实可以使用setPeriodic()
//        long duration = params.getExtras().getLong(WORK_DURATION_KEY);
//
//        // 使用一个handler处理程序来延迟jobFinished（）的执行。
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sendMessage(MSG_COLOR_STOP, params.getJobId());
//                jobFinished(params, false);
//            }
//        }, duration);
//        Log.i(TAG, "on start job: " + params.getJobId());

        // 返回true，很多工作都会执行这个地方，我们手动结束这个任务
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // 停止跟踪这些作业参数，因为我们已经完成工作。
        sendMessage(MSG_COLOR_STOP, params.getJobId());
        Log.i(TAG, "on stop job: " + params.getJobId());

        return false;
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        // 如果此服务由JobScheduler启动，则没有回调Messenger。
        // 它仅在MainActivity在Intent中使用回调函数调用startService()时存在。
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }

        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
    }
}
