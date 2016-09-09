package com.soz.sozTest.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.soz.hook.proxy.AMSHookHandler;
import com.soz.log.Logger;
import com.soz.sozTest.plugin.proxyHook.ServiceHookActivity;
import com.soz.utils.ClassLoaderUtils;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class StubService extends Service{
    Logger mLogger = new Logger("StubService");

    @Override
    public void onCreate() {
        mLogger.i("onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        mLogger.i("onStart");
        super.onStart(intent, startId);
        Intent targetIntent = intent.getParcelableExtra(AMSHookHandler.EXTRA_TARGET_INTENT);
        ServiceInfo info = selectPluginServiceInfo(targetIntent);
        mLogger.i("info = " + (info == null ? "null" : info.toString()));
        if (info == null) return;
        try {
            Service service = ClassLoaderUtils.createService(this, info);
            service.onBind(targetIntent);
        } catch (Exception e) {
            mLogger.i("createService failed");
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mLogger.i("onBind");
        return null;
    }

    private ServiceInfo selectPluginServiceInfo(Intent pluginIntent) {
        if (ServiceHookActivity.mServiceCache == null) return null;
        for (ComponentName componentName:ServiceHookActivity.mServiceCache.keySet()) {
            if (componentName.equals(pluginIntent.getComponent())) {
                return ServiceHookActivity.mServiceCache.get(componentName);
            }
        }
        return null;
    }
}
