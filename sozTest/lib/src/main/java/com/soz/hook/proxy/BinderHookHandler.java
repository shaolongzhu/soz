package com.soz.hook.proxy;

import android.content.ClipData;
import android.os.IBinder;

import com.soz.log.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhushaolong on 8/23/16.
 */
public class BinderHookHandler implements InvocationHandler {
    Logger mLogger = new Logger("BinderHookHandler");
    Object base;

    public BinderHookHandler(IBinder base, Class<?> stubClass) {
        try {
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface", IBinder.class);
            this.base = asInterfaceMethod.invoke(null, base);
        } catch (Exception e) {
            throw new RuntimeException("hooked failed");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getPrimaryClip".equals(method.getName())) {
            mLogger.i("hook getPrimaryClip");
            return ClipData.newPlainText(null, "you are hooked");
        }

        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }

        return method.invoke(base, args);
    }
}
