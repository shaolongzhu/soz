package com.soz.hook;

import android.os.Bundle;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;

/**
 * hook plugin base activity, all plugin should extend it
 * Created by zhushaolong on 8/19/16.
 */
public abstract class BaseHookPluginActivity extends BaseActivity {
    Logger mLogger = new Logger("BaseHookPluginActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
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
