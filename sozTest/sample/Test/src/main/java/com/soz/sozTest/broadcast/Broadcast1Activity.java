package com.soz.sozTest.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;

/**
 * Created by zhushaolong on 11/11/16.
 */

public class Broadcast1Activity extends BaseActivity {
    Logger mLogger = new Logger("Broadcast1Activity");
    private static final String INTENT_ACTION = "com.soz.sozTest.broadcast1";

    @Override
    public void onCreate(Bundle onSaveInstancedState) {
        mLogger.i("onCreate");
        super.onCreate(onSaveInstancedState);
        this.sendBroadcast();
    }

    private void sendBroadcast() {
        mLogger.i("sendBroadcast");
        Intent intent = new Intent();
        IntentFilter intentFilter = new IntentFilter();
        intent.setAction(INTENT_ACTION);
        this.sendBroadcast(intent);
    }
}
