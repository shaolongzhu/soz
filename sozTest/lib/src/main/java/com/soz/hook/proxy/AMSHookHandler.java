package com.soz.hook.proxy;

import android.content.ComponentName;
import android.content.Intent;

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
        if ("startActivity".equals(method.getName())) {
            Intent rawIntent;
            int index = 0;
            for (int i = 0; i < args.length; ++i) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            rawIntent = (Intent) args[index];
            Intent newIntent = new Intent();
            ComponentName componentName = new ComponentName(ConstantUtils.PACKAGE_NAME_1, ConstantUtils.STUB_ACTIVITY);
            newIntent.setComponent(componentName);
            newIntent.putExtra(EXTRA_TARGET_INTENT, rawIntent);
            args[index] = newIntent;
            mLogger.i("hook success");
            return method.invoke(this.mBase, args);
        }
        return method.invoke(this.mBase, args);
    }
}
