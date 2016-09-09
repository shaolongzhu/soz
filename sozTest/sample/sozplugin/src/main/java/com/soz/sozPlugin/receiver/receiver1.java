package com.soz.sozPlugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.soz.utils.ConstantUtils;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class receiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I am receiver1", Toast.LENGTH_SHORT).show();
        context.sendBroadcast(new Intent(ConstantUtils.PLUGIN_ACTION_RECEIVER_2));
    }
}
