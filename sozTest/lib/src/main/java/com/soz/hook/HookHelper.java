package com.soz.hook;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;

import com.soz.hook.proxy.AMSHookHandler;
import com.soz.hook.proxy.ActivityThreadHandlerCallback;
import com.soz.hook.proxy.BinderHookProxyHandler;
import com.soz.hook.proxy.CheatInstrumentation;
import com.soz.hook.proxy.PMSHookHandler;
import com.soz.utils.ClassLoaderUtils;
import com.soz.utils.FileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

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

    public static void HookPackageManager() throws Exception {
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

    public static void PatchClassLoader(ClassLoader cl, File apkFile, File optDexFile) throws Exception {
        // 获取 pathList 对象
        Field dexPathListField = DexClassLoader.class.getSuperclass().getDeclaredField("pathList");
        dexPathListField.setAccessible(true);
        Object pathList = dexPathListField.get(cl);

        // 获取 pathList: Element[] dexElements 对象
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        Object[] dexElements = (Object[]) dexElementsField.get(pathList);

        // Element 类型
        Class<?> elementsClass = dexElements.getClass().getComponentType();
        Object[] newElements = (Object[]) Array.newInstance(elementsClass, dexElements.length + 1);
        // 创建 Element(File dir, boolean isDirectory, File zip, DexFile dexFile)
        Constructor<?> constructor = elementsClass.getConstructor(File.class, boolean.class, File.class, DexFile.class);
        Object o = constructor.newInstance(apkFile, false, apkFile, DexFile.loadDex(apkFile.getCanonicalPath(), optDexFile.getAbsolutePath(), 0));
        Object[] toAddElement = new Object[] {o};
        System.arraycopy(dexElements, 0, newElements, 0, dexElements.length);
        System.arraycopy(toAddElement, 0, newElements, dexElements.length, toAddElement.length);

        // 替换
        dexElementsField.set(pathList, newElements);
    }

    private static ApplicationInfo generateApplicationInfo(File apkFile) throws Exception{
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");

        Class<?> packageParser$PackageClass = Class.forName("android.content.pm.PackageParser$Package");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Method generateApplicationInfoMethod = packageParserClass.getDeclaredMethod("generateApplicationInfo",
                packageParser$PackageClass, int.class, packageUserStateClass);
        Object packageParser = packageParserClass.newInstance();
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

        Object packageObject = parsePackageMethod.invoke(packageParser, apkFile, 0);

        Object defaultPackageUserState = packageUserStateClass.newInstance();

        ApplicationInfo applicationInfo = (ApplicationInfo) generateApplicationInfoMethod.invoke(packageParser,
                packageObject, 0, defaultPackageUserState);

        String apkPath = apkFile.getPath();

        applicationInfo.sourceDir = apkPath;
        applicationInfo.publicSourceDir = apkPath;

         return applicationInfo;
    }

    public static void CustomClassloader(Context context, File apkFile) throws Exception {
        // 获取 ActivityThread 对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        Field mPackageField = activityThreadClass.getDeclaredField("mPackages");
        mPackageField.setAccessible(true);
        Map mPackages = (Map) mPackageField.get(currentActivityThread);

        Class<?> compatibilityInfo = Class.forName("android.content.res.CompatibilityInfo");
        Method getPackageInfoNoCheckMethod = activityThreadClass.getDeclaredMethod("getPackageInfoNoCheck", ApplicationInfo.class, compatibilityInfo);

        Field defaultCompatibilityInfoField = compatibilityInfo.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
        defaultCompatibilityInfoField.setAccessible(true);
        Object defaultCompatibilityInfo = defaultCompatibilityInfoField.get(null);

        ApplicationInfo applicationInfo = generateApplicationInfo(apkFile);
        Object loadedApk = getPackageInfoNoCheckMethod.invoke(currentActivityThread, applicationInfo, defaultCompatibilityInfo);

        String odexPath = FileUtils.getPluginOptDexDir(context).getPath();
        String libDir = FileUtils.getPluginLibDir(context).getPath();
        ClassLoader classLoader = new DexClassLoader(apkFile.getPath(), odexPath, libDir, ClassLoader.getSystemClassLoader());
        Field mClassLoaderFile = loadedApk.getClass().getDeclaredField("mClassLoader");
        mClassLoaderFile.setAccessible(true);
        mClassLoaderFile.set(loadedApk, classLoader);
        WeakReference weakReference = new WeakReference(loadedApk);
        mPackages.put(applicationInfo.packageName, weakReference);
    }

    /**
     * 解析APK中的 <receiver>
     * @param apkFile
     * @return
     */
    private static Map<ActivityInfo, List<? extends IntentFilter>> parserReceivers(File apkFile) throws Exception {
        Map<ActivityInfo, List<? extends IntentFilter>> cache = new HashMap<ActivityInfo, List<? extends IntentFilter>>();
        Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
        Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);

        Object packageParser = packageParserClass.newInstance();
        Object packageObject = parsePackageMethod.invoke(packageParser, apkFile, PackageManager.GET_RECEIVERS);
        Field receiversField = packageObject.getClass().getDeclaredField("receivers");
        receiversField.setAccessible(true);
        List receivers = (List) receiversField.get(packageObject);

        Class<?> packageParser$ActivityClass = Class.forName("android.content.pm.PackageParser$Activity");
        Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
        Class<?> userHandler = Class.forName("android.os.UserHandle");
        Method getCallingUserIdMethod = userHandler.getDeclaredMethod("getCallingUserId");
        int userId = (Integer) getCallingUserIdMethod.invoke(null);
        Object packageUserState = packageUserStateClass.newInstance();

        Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
        Field intentField = componentClass.getDeclaredField("intents");

        Method generateReceiverInfo = packageParserClass.getDeclaredMethod("generateActivityInfo",
                packageParser$ActivityClass, int.class, packageUserStateClass, int.class);
        for (Object receiver : receivers) {
            ActivityInfo info = (ActivityInfo) generateReceiverInfo.invoke(packageParser, receiver, 0, packageUserState, userId);
            List<? extends IntentFilter> filters = (List<? extends IntentFilter>) intentField.get(receiver);
            cache.put(info, filters);
        }

        return cache;
    }

    public static void loadReceiver(Context context, File apkFile) throws Exception {
        Map<ActivityInfo, List<? extends IntentFilter>> cache = parserReceivers(apkFile);
        ClassLoader cl = null;
        for (ActivityInfo activityInfo:cache.keySet()) {
            List<? extends IntentFilter> intentFilters = cache.get(activityInfo);
            if (cl == null) {
                cl = ClassLoaderUtils.getPluginClassLoader(context, apkFile);
            }
            for (IntentFilter intentFilter:intentFilters) {
                BroadcastReceiver receiver = (BroadcastReceiver) cl.loadClass(activityInfo.name).newInstance();
                context.registerReceiver(receiver, intentFilter);
            }
        }
    }

}
