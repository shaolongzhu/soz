package com.soz.hook;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhushaolong on 8/19/16.
 */
public class HookHelper {
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
}
