package com.example.chtlei.mydemo.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by chtlei on 18-10-25.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null)
            return;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        switch (intent.getAction()){
            case Constant.REPLY:{
                //get the content from the input
                Bundle input = RemoteInput.getResultsFromIntent(intent);
                if (input == null)
                    return;
                //Do something
                Toast.makeText(context, input.getCharSequence(Constant.KEY_TEXT_REPLY), Toast.LENGTH_SHORT).show();
                manager.cancel(Constant.ID_FOR_NORMAL_ACTION);
                break;
            }
            case Constant.MAKE_AS_READ:
                //Do something
                Toast.makeText(context, Constant.MAKE_AS_READ, Toast.LENGTH_SHORT).show();
                manager.cancel(Constant.ID_FOR_NORMAL_ACTION);
                break;
            case Constant.DELETE:
                //Do something
                Toast.makeText(context, Constant.DELETE, Toast.LENGTH_SHORT).show();
                manager.cancel(Constant.ID_FOR_NORMAL_ACTION);
                break;
            case Constant.REPLY_MESSAGING:{
                //get the content from the input
                Bundle input = RemoteInput.getResultsFromIntent(intent);
                if (input == null)
                    return;
                //Do something
//                Intent intent1 = new Intent();
//                intent1.setAction(IntentAction.MESSAGING_REPLY);
//                intent1.putExtra(BundleKey.TEXT, input.getCharSequence(NotificationUtil.KEY_TEXT_REPLY));
//                context.sendBroadcast(intent1);


                String text = input.getString(Constant.KEY_TEXT_REPLY);

                List<Message> list = new Gson().fromJson(PrefUtil.getMessage(context)
                        , new TypeToken<List<Message>>() {}.getType());
                list.add(new Message(text, System.currentTimeMillis(), null));

                PrefUtil.setMessage(context, new Gson().toJson(list));

                NotificationUtil.messagingUpdate(context, list);
                break;
            }
            case Constant.BACK:
                //Do something
                Toast.makeText(context, Constant.BACK, Toast.LENGTH_SHORT).show();
                break;
            case Constant.NEXT:
                //Do something
                Toast.makeText(context, Constant.NEXT, Toast.LENGTH_SHORT).show();
                break;
            case Constant.PAUSE:
                //Do something
                Toast.makeText(context, Constant.PAUSE, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
