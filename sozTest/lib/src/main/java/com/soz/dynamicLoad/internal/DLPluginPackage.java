package com.soz.dynamicLoad.internal;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by zhushaolong on 8/8/16.
 */
public class DLPluginPackage {
    public String packageName;
    public String defaultActivity;
    public DexClassLoader classLoader;
    public AssetManager assetManager;
    public Resources resources;
    public PackageInfo packageInfo;

    public DLPluginPackage(DexClassLoader loader, Resources resources,
                           PackageInfo packageInfo) {
        this.classLoader = loader;
        this.resources = resources;
        this.packageInfo = packageInfo;
        this.assetManager = resources.getAssets();
        this.packageName = packageInfo.packageName;
        this.defaultActivity = this.getDefaultActivity();
    }

    private String getDefaultActivity() {
        if (this.packageInfo.activities != null && this.packageInfo.activities.length > 0) {
            return this.packageInfo.activities[0].name;
        }
        return "";
    }
}
