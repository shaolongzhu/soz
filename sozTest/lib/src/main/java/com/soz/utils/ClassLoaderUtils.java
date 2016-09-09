package com.soz.utils;

import android.app.Service;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class ClassLoaderUtils {
    private ClassLoaderUtils() {
    }

    public static ClassLoader getPluginClassLoader(Context context, File apkFile) throws Exception{
        return new DexClassLoader(apkFile.getPath(),
                FileUtils.getPluginOptDexDir(context).getPath(),
                FileUtils.getPluginLibDir(context).getPath(),
                context.getClassLoader());
    }

    public static Service createService(Context context, ServiceInfo serviceInfo) throws Exception {
        IBinder token = new Binder();

        Class<?> createServiceDataClass = Class.forName("android.app.ActivityThread$CreateServiceData");
        Constructor<?> constructor = createServiceDataClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object createServiceData = constructor.newInstance();

        Field tokenField = createServiceDataClass.getDeclaredField("token");
        tokenField.setAccessible(true);
        tokenField.set(createServiceData, token);

        serviceInfo.applicationInfo.packageName = context.getPackageName();
        Field infoField = createServiceDataClass.getDeclaredField("info");
        infoField.setAccessible(true);
        infoField.set(createServiceData, serviceInfo);

        Class<?> compatibilityClass = Class.forName("android.content.res.CompatibilityInfo");
        Field defaultCompatibilityField = compatibilityClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultCompatibilityField.setAccessible(true);
        Object defaultCompatibility = defaultCompatibilityField.get(null);
        defaultCompatibilityField.set(createServiceData, defaultCompatibility);

        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        Method handleCreateServiceMethod = activityThreadClass.getDeclaredMethod("handleCreateService", createServiceDataClass);
        handleCreateServiceMethod.setAccessible(true);
        handleCreateServiceMethod.invoke(currentActivityThread, createServiceData);

        Field mServicesField = activityThreadClass.getDeclaredField("mServices");
        mServicesField.setAccessible(true);
        Map mServices = (Map) mServicesField.get(currentActivityThread);
        Service service = (Service) mServices.get(token);
        mServices.remove(token);

        return service;
    }
}
