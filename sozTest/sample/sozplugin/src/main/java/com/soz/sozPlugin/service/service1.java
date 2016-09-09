package com.soz.sozPlugin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.soz.log.Logger;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class service1 extends Service {
    Logger mLogger = new Logger("service1");

    @Override
    public void onCreate() {
        mLogger.i("onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        mLogger.i("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mLogger.i("onBInd");
        return null;
    }
}
