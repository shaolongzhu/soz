package com.soz.sozTest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.soz.log.Logger;

/**
 * Created by zhushaolong on 11/11/16.
 */

public class BroadcastReceiver1 extends BroadcastReceiver {
    Logger mLogger = new Logger("BroadcastReceiver1");

    @Override
    public void onReceive(Context context, Intent intent) {
        mLogger.i("onReceive");
    }
}
