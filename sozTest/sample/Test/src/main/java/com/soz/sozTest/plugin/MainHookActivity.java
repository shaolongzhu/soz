package com.soz.sozTest.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.sozTest.plugin.proxyHook.AMSHookActivity;
import com.soz.sozTest.plugin.proxyHook.ActHookActivity;
import com.soz.sozTest.plugin.proxyHook.BinderHookActivity;
import com.soz.sozTest.plugin.proxyHook.ClassLoaderHookActivity;
import com.soz.sozTest.plugin.proxyHook.PMSHookActivity;
import com.soz.sozTest.plugin.proxyHook.ProviderHookActivity;
import com.soz.sozTest.plugin.proxyHook.ProxyHookActivity;
import com.soz.sozTest.plugin.proxyHook.ReceiverHookActivity;
import com.soz.sozTest.plugin.proxyHook.ServiceHookActivity;
import com.soz.utils.AppManagerUtils;
import com.soz.utils.ConstantType;
import com.soz.utils.ConstantUtils;

/**
 * Created by zhushaolong on 8/19/16.
 */
public class MainHookActivity extends BaseActivity {
    Logger mLogger = new Logger("MainHookActivity");
    RadioGroup mRadioGroup;
    private int mClassloaderType = ConstantType.CLASSLOADER_PATCH;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_hook_main);
        this.initView();
        this.initEvent();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) this.findViewById(R.id.radio_group);
    }

    private void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_patch) {
                    mLogger.i("check change[patch]");
                    MainHookActivity.this.mClassloaderType = ConstantType.CLASSLOADER_PATCH;
                } else if (checkedId == R.id.radio_custom) {
                    mLogger.i("check change[custom]");
                    MainHookActivity.this.mClassloaderType = ConstantType.CLASSLOADER_CUSTOM;
                }
            }
        });
    }

    public void jumpToProxyHook(View view) {
        mLogger.i("jumpToProxyHook");
        Intent intent = new Intent(MainHookActivity.this, ProxyHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToBinderHook(View view) {
        mLogger.i("jumpToBinderHook");
        Intent intent = new Intent(MainHookActivity.this, BinderHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToAMSHook(View view) {
        mLogger.i("jumpToAMSHook");
        Intent intent = new Intent(MainHookActivity.this, AMSHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToPMSHook(View view) {
        mLogger.i("jumpToPMSHook");
        Intent intent = new Intent(MainHookActivity.this, PMSHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToActHook(View view) {
        mLogger.i("jumpToActHook");
        Intent intent = new Intent(MainHookActivity.this, ActHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToClassloaderHook(View view) {
        mLogger.i("jumpToClassloaderHook");
        Intent intent = new Intent(MainHookActivity.this, ClassLoaderHookActivity.class);
        intent.putExtra(ConstantUtils.CLASSLOADER_TYPE, this.mClassloaderType);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToReceiverHook(View view) {
        mLogger.i("jumpToReceiverHook");
        Intent intent = new Intent(MainHookActivity.this, ReceiverHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToServiceHook(View view) {
        mLogger.i("jumpToServiceHook");
        Intent intent = new Intent(MainHookActivity.this, ServiceHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToProviderHook(View view) {
        mLogger.i("jumpToProviderHook");
        Intent intent = new Intent(MainHookActivity.this, ProviderHookActivity.class);
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
