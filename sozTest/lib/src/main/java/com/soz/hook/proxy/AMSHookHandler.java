package com.soz.hook.proxy;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;

import com.soz.log.Logger;
import com.soz.utils.ConstantUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by zhushaolong on 9/1/16.
 */
public class AMSHookHandler implements InvocationHandler {
    Logger mLogger = new Logger("AMSHookHandler");
    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";
    private Object mBase;

    public AMSHookHandler(Object base) {
        this.mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        mLogger.i("[AMS]you are hooked");
        mLogger.i("method: " + method.getName() + " called with args " + Arrays.toString(args));
        if ("startActivity".equals(method.getName())
                || "startService".equals(method.getName())) {
            Pair<Integer, Intent> integerIntentPair = findFirstIntentOfArgs(args);
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(ConstantUtils.PACKAGE_NAME_1, getStubClassName(method.getName()));
            newIntent.setComponent(componentName);
            newIntent.putExtra(EXTRA_TARGET_INTENT, integerIntentPair.second);
            args[integerIntentPair.first] = newIntent;
            mLogger.i("hook startActivity success");
            return method.invoke(this.mBase, args);
        }
        return method.invoke(this.mBase, args);
    }

    private String getStubClassName(String methodName) {
        if ("startActivity".equals(methodName)) {
            return ConstantUtils.STUB_ACTIVITY;
        } else {
            return ConstantUtils.STUB_SERVICE;
        }
    }

    private Pair<Integer, Intent> findFirstIntentOfArgs(Object... args) {
        int index = 0;
        for (int i = 0; i < args.length; ++i) {
            if (args[i] instanceof Intent) {
                index = i;
                break;
            }
        }
        return Pair.create(index, (Intent) args[index]);
    }
}
