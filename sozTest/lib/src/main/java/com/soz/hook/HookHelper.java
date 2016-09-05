package com.soz.hook;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;

import com.soz.hook.proxy.AMSHookHandler;
import com.soz.hook.proxy.ActivityThreadHandlerCallback;
import com.soz.hook.proxy.BinderHookProxyHandler;
import com.soz.hook.proxy.CheatInstrumentation;
import com.soz.hook.proxy.PMSHookHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by zhushaolong on 8/19/16.
 */
public final class HookHelper {
    private static final String CLIPBOARD_SERVICE = "clipboard";

    private HookHelper() {
    }

    public static void attachContext() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // create proxy object
        Instrumentation cheatInstrumentation =  new CheatInstrumentation(mInstrumentation);
        mInstrumentationField.set(currentActivityThread, cheatInstrumentation);
    }

    public static void HookClipBoardService() throws Exception {
        Class<?> serviceManagerClass = Class.forName("android.os.ServiceManager");
        Method getServiceMethod = serviceManagerClass.getDeclaredMethod("getService", String.class);
        getServiceMethod.setAccessible(true);
        IBinder rawBinder = (IBinder) getServiceMethod.invoke(null, CLIPBOARD_SERVICE);
        IBinder hookBinder = (IBinder) Proxy.newProxyInstance(serviceManagerClass.getClassLoader(), new Class<?>[]{ IBinder.class }, new BinderHookProxyHandler(rawBinder));
        Field cacheField = serviceManagerClass.getDeclaredField("sCache");
        cacheField.setAccessible(true);
        Map<String, IBinder> cache = (Map) cacheField.get(null);
        cache.put(CLIPBOARD_SERVICE, hookBinder);
    }

    public static void HookActivityManager() throws Exception {
        Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        // 获取 gDefault 字段
        Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);
        // gDefault 为singleton对象
        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object rawActivityManager = mInstanceField.get(gDefault);
        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxyActivityManager = Proxy.newProxyInstance(ActivityManager.class.getClassLoader(),
                new Class<?>[]{iActivityManagerInterface}, new AMSHookHandler(rawActivityManager));
        mInstanceField.set(gDefault, proxyActivityManager);
    }

    public static void HookPackageManager(Context context) throws Exception {
        // 获取 activityThread 对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object sPackageManager = sPackageManagerField.get(currentActivityThread);

        Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
        Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(), new Class[] {iPackageManagerInterface},
                new PMSHookHandler(sPackageManager));

        sPackageManagerField.set(currentActivityThread, proxy);

        PackageManager pm = context.getPackageManager();
        Field mPmField = pm.getClass().getDeclaredField("mPM");
        mPmField.setAccessible(true);
        mPmField.set(pm, proxy);
    }

    public static void HookActivityThreadHandler() throws Exception {
        // 获取 activityThread 对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(currentActivityThread);

        Field mCallbackField = Handler.class.getDeclaredField("mCallback");
        mCallbackField.setAccessible(true);
        mCallbackField.set(mH, new ActivityThreadHandlerCallback(mH));
    }
}
