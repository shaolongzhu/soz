package com.soz.sozPlugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class receiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I am receiver2", Toast.LENGTH_SHORT).show();
    }
}
