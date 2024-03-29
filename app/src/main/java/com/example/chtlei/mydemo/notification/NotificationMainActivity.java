package com.example.chtlei.mydemo.notification;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.example.chtlei.mydemo.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chtlei on 18-10-25.
 */

public class NotificationMainActivity extends Activity implements NotificationAdapter.OnItemClickListener{
    @BindView(R.id.cb_sound)
    CheckBox cbSound;
    @BindView(R.id.cb_lock)
    CheckBox cbLock;
    @BindView(R.id.cb_head)
    CheckBox cbHead;
    @BindView(R.id.cb_auto_cancel)
    CheckBox cbAutoCancel;
    @BindView(R.id.cb_only)
    CheckBox cbOnly;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Context context;

    private List<String> list = new ArrayList<>();

    private List<Message> messageList = new ArrayList<>();

    private MessageReceiver receiver= new MessageReceiver();
    private IntentFilter filter = new IntentFilter();

    public static boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);

        context = getApplicationContext();

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtil.createNotificationChannel(context,"chtlei","chtlei");
        }

        initView();
    }

    private void initView() {
        list.add("Normal");
        list.add("Normal with action");
        list.add("Big Text");
        list.add("Inbox");
        list.add("Group");
        list.add("Big Picture");
        list.add("Messaging");
        list.add("Media");
        list.add("Custom View");
        list.add("Cancel");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NotificationAdapter adapter = new NotificationAdapter(getApplicationContext(), list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        messageList.add(new Message("What do you plan to do on the weekend?", System.currentTimeMillis(), "Smile"));
        messageList.add(new Message("Sleep.", System.currentTimeMillis(), "Tracy"));
        messageList.add(new Message("Go to Beijing..", System.currentTimeMillis(), "SB"));
        messageList.add(new Message("Play football", System.currentTimeMillis(), "Naocan"));
        messageList.add(new Message("Coding...", System.currentTimeMillis(), "Developer"));

        PrefUtil.setMessage(context, new Gson().toJson(messageList));

        filter.addAction(Constant.MESSAGING_REPLY);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinished = true;
        unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                NotificationUtil.normal(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), cbOnly.isChecked());
                break;
            case 1:
                NotificationUtil.normalWithAction(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked());
                break;
            case 2:
                NotificationUtil.bigText(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), cbOnly.isChecked());
                break;
            case 3:
                NotificationUtil.inbox(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), cbOnly.isChecked());
                break;
            case 4:
                NotificationUtil.group(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked());
                break;
            case 5:
                NotificationUtil.bigPicture(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), cbOnly.isChecked());
                break;
            case 6:
                NotificationUtil.messaging(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), messageList);
                break;
            case 7:
                NotificationUtil.media(context, cbSound.isChecked(), cbLock.isChecked());
                break;
            case 8:
                startService(new Intent(this, NotificationService.class));
                break;
            default:
                NotificationUtil.cancel(context);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, NotificationSecondActivity.class));
        return super.onOptionsItemSelected(item);
    }

    class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null||intent.getAction() == null)
                return;
            switch (intent.getAction()){
                case Constant.MESSAGING_REPLY:
                    messageList.add(new Message(intent.getStringExtra(Constant.TEXT), System.currentTimeMillis(), null));
                    NotificationUtil.messaging(context, cbSound.isChecked(), cbLock.isChecked(), cbHead.isChecked(), cbAutoCancel.isChecked(), messageList);
                    break;
            }
        }
    }
}
