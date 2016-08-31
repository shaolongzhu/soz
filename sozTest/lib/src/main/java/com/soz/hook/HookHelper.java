package com.soz.hook;

import android.app.Instrumentation;
import android.os.IBinder;

import com.soz.hook.proxy.BinderHookProxyHandler;
import com.soz.hook.proxy.CheatInstrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by zhushaolong on 8/19/16.
 */
public class HookHelper {
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

    public static void HookClipBoardService() throws Exception{
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
}
