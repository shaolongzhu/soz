package com.example.soz.dynamicLoad;

import android.os.Bundle;

import com.example.soz.activity.BaseActivity;
import com.example.soz.log.Logger;

/**
 * plugin base activity, all plugin activity should extend it.
 * Created by zhushaolong on 8/8/16.
 */
public abstract class DLBasePluginActivity extends BaseActivity implements DLPlugin{
    Logger mLogger = new Logger("DLBasePluginActivity");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        mLogger.i("onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        mLogger.i("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        mLogger.i("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        mLogger.i("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mLogger.i("onDestroy");
        super.onDestroy();
    }
}
