package com.example.soz.dynamicLoad.internal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.soz.dynamicLoad.DLPlugin;
import com.example.soz.dynamicLoad.utils.DLConstants;
import com.example.soz.log.Logger;

import java.lang.reflect.Constructor;

/**
 * dynamic load for initialization
 * Created by zhushaolong on 8/8/16.
 */
public class DLProxyImpl {
    Logger mLogger = new Logger("DLProxyImpl");

    private String mPackageName;
    private String mClass;

    private AssetManager mAssertManager;
    private Resources mResources;
    private Resources.Theme mTheme;

    private Activity mProxyActivity;
    private DLPlugin mPluginActivity;

    private DLPluginManager mPluginManager;
    private DLPluginPackage mPluginPackage;

    public DLProxyImpl(Activity activity) {
        this.mProxyActivity = activity;
    }

    public void onCreate(Intent intent) {
        mLogger.i("onCreate");
        this.mPackageName = intent.getStringExtra(DLConstants.EXTRA_PACKAGE_NAME);
        this.mClass = intent.getStringExtra(DLConstants.EXTRA_CLASS_NAME);
        mLogger.i("mPackageName = " + this.mPackageName + ", mClass = " + this.mClass);

        this.mPluginManager = DLPluginManager.getInstance(this.mProxyActivity);
        this.mPluginPackage = this.mPluginManager.getPackage(this.mPackageName);

        initializeActivityInfo();
        launchTargetActivity();
    }

    private void initializeActivityInfo() {
        PackageInfo packageInfo = this.mPluginPackage.packageInfo;
        if (packageInfo.activities != null && packageInfo.activities.length > 0) {
            if (this.mClass == null) {
                this.mClass = packageInfo.activities[0].name;
            }
        }
    }

    private void launchTargetActivity() {
        try {
            Class<?> localClass = this.getClassLoader().loadClass(this.mClass);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Class[]{});
            this.mPluginActivity = (DLPlugin) instance;
            ((DLAttachable) this.mProxyActivity).onAttach(this.mPluginActivity);
            mLogger.i("instance = " + instance);
            Bundle bundle = new Bundle();
            bundle.putInt(DLConstants.FROM, DLConstants.FROM_EXTERNAL);
            this.mPluginActivity.onCreate(bundle);
        } catch (Exception e) {
            mLogger.i("[launchTargetActivity]exception");
            e.printStackTrace();
        }
    }

    private ClassLoader getClassLoader() {
        return this.mPluginPackage.classLoader;
    }
}
