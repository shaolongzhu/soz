package com.soz.dynamicLoad.internal;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import com.soz.dynamicLoad.DLBasePluginActivity;
import com.soz.dynamicLoad.DLProxyActivity;
import com.soz.dynamicLoad.utils.DLConstants;
import com.soz.log.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * singleton for plugin manager
 * Created by zhushaolong on 8/8/16.
 */
public class DLPluginManager {
    private Logger mLogger = new Logger("DLPluginManager");
    private static DLPluginManager sInstance;
    private Context mContext;
    private String mNativeLibPath;
    private String mOutputDexPath;
    private Map<String, DLPluginPackage> mPluginHolder = new HashMap<String, DLPluginPackage>();

    public static final int START_RESULT_SUCCESS = 0;
    public static final int START_RESULT_NO_PKG = 1;
    public static final int START_RESULT_NO_CLASS = 2;
    public static final int START_RESULT_ERROR = 3;

    private DLPluginManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static DLPluginManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DLPluginManager.class) {
                if (sInstance == null) {
                    sInstance = new DLPluginManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * getting DLPluginPackage depends package name
     * @param packageName
     * @return
     */
    public DLPluginPackage getPackage(String packageName) {
        return mPluginHolder.get(packageName);
    }

    /**
     * load plugin apk
     * @param dexPath
     * @return
     */
    public DLPluginPackage loadAPK(String dexPath) {
        PackageInfo packageInfo = this.mContext.getPackageManager().getPackageArchiveInfo(dexPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) return null;
        DLPluginPackage pluginPackage = preparePluginEnv(packageInfo, dexPath);
        return pluginPackage;
    }

    /**
     * prepare plugin environment
     * @param packageInfo
     * @param dexPath
     * @return
     */
    private DLPluginPackage preparePluginEnv(PackageInfo packageInfo, String dexPath) {

        DLPluginPackage pluginPackage = this.mPluginHolder.get(packageInfo.packageName);
        if (pluginPackage != null) {
            return pluginPackage;
        }
        DexClassLoader classLoader = createClassLoader(dexPath);
        AssetManager assetManager = createAssetManager(dexPath);
        Resources resources = createResources(assetManager);
        pluginPackage = new DLPluginPackage(classLoader, resources, packageInfo);
        this.mPluginHolder.put(packageInfo.packageName, pluginPackage);
        return pluginPackage;
    }

    /**
     * create asset manager for plugin
     * @param dexPath
     * @return
     */
    private AssetManager createAssetManager(String dexPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
            method.invoke(assetManager, dexPath);
            return assetManager;
        } catch (Exception e) {
            mLogger.i("[createAssetManager]exception");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * create classloader for plugin
     * @param dexPath
     * @return
     */
    private DexClassLoader createClassLoader(String dexPath) {
        this.mNativeLibPath = null;
        File file = this.mContext.getDir("dex", Context.MODE_PRIVATE);
        this.mOutputDexPath = file.getAbsolutePath();
        DexClassLoader classLoader = new DexClassLoader(dexPath, this.mOutputDexPath, this.mNativeLibPath, this.mContext.getClassLoader());
        return classLoader;
    }

    /**
     * create resources for plugin
     * @param assetManager
     * @return
     */
    private Resources createResources(AssetManager assetManager) {
        Resources superRes = this.mContext.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

    /**
     * start plugin
     * @param context
     * @param dlIntent
     * @return
     */
    public int startPluginActivity(Context context, DLIntent dlIntent) {
        String packageName = dlIntent.getPluginPackage();
        if (TextUtils.isEmpty(packageName)) throw new NullPointerException("disallow null packageName");

        DLPluginPackage dlPluginPackage = mPluginHolder.get(packageName);
        if (dlPluginPackage == null) return START_RESULT_NO_PKG;

        String className = getPluginActivityFullPath(dlIntent, dlPluginPackage);
        Class<?> clazz = loadPluginClass(dlPluginPackage.classLoader, className);
        if (clazz == null) return START_RESULT_NO_CLASS;
        Class<? extends Activity> activityClass = getProxyActivityClass(clazz);
        if (activityClass == null) return START_RESULT_ERROR;

        dlIntent.putExtra(DLConstants.EXTRA_PACKAGE_NAME, packageName);
        dlIntent.putExtra(DLConstants.EXTRA_CLASS_NAME, className);
        dlIntent.setClass(mContext, activityClass);
        context.startActivity(dlIntent);

        return START_RESULT_SUCCESS;
    }

    private String getPluginActivityFullPath(DLIntent dlIntent, DLPluginPackage dlPluginPackage) {
        String className = dlIntent.getPluginClass();
        className = className == null ? dlPluginPackage.defaultActivity : className;
        if (className.startsWith(".")) {
            className = dlIntent.getPluginPackage() + className;
        }
        return className;
    }

    private Class<?> loadPluginClass(ClassLoader classLoader, String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            mLogger.i("can not find class: " + className);
            e.printStackTrace();
        }
        return clazz;
    }

    private Class<? extends Activity> getProxyActivityClass(Class<?> clazz) {
        Class<? extends Activity> activity = null;
        if (DLBasePluginActivity.class.isAssignableFrom(clazz)) {
            activity = DLProxyActivity.class;
        }
        return activity;
    }
}
