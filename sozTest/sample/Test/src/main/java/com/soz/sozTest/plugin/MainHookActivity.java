package com.soz.sozTest.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.sozTest.plugin.proxyHook.AMSHookActivity;
import com.soz.sozTest.plugin.proxyHook.BinderHookActivity;
import com.soz.sozTest.plugin.proxyHook.PMSHookActivity;
import com.soz.sozTest.plugin.proxyHook.ProxyHookActivity;
import com.soz.utils.AppManagerUtils;

/**
 * Created by zhushaolong on 8/19/16.
 */
public class MainHookActivity extends BaseActivity {
    Logger mLogger = new Logger("MainHookActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_hook_main);
    }

    public void jumpToProxyHook(View view) {
        Intent intent = new Intent(MainHookActivity.this, ProxyHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToBinderHook(View view) {
        Intent intent = new Intent(MainHookActivity.this, BinderHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToAMSHook(View view) {
        Intent intent = new Intent(MainHookActivity.this, AMSHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToPMSHook(View view) {
        Intent intent = new Intent(MainHookActivity.this, PMSHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    @Override
    public void onResume() {
        mLogger.i("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        mLogger.i("onPuase");
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
