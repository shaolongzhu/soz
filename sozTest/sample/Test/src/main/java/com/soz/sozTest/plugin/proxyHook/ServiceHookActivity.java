package com.soz.sozTest.plugin.proxyHook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.view.View;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.utils.ConstantUtils;
import com.soz.utils.FileUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class ServiceHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ServiceHookActivity");
    public static Map<ComponentName, ServiceInfo> mServiceCache;

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try {
            HookHelper.HookActivityManager();
            FileUtils.extractAssets(newBase, ConstantUtils.APK_FILE_2);
            File apkFile = this.getFileStreamPath(ConstantUtils.APK_FILE_2);
            File dexFile = this.getFileStreamPath(ConstantUtils.DEX_FILE_2);
            HookHelper.PatchClassLoader(this.getClassLoader(), apkFile, dexFile);
            mServiceCache = HookHelper.loadServices(apkFile);
            if (mServiceCache == null) {
                mLogger.i("mServiceCache is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_service_hook);
    }

    public void startPluginService(View view) {
        mLogger.i("startPluginService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(ConstantUtils.PACKAGE_NAME_2, ConstantUtils.PLUGIN_SERVICE_1));
        this.startService(intent);
    }
}
