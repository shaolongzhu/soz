package com.soz.hook.proxy;

import android.os.IBinder;

import com.soz.log.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhushaolong on 8/31/16.
 */
public class BinderHookProxyHandler implements InvocationHandler {
    Logger mLogger = new Logger("BinderHookProxyHandler");
    IBinder mBase;
    Class<?> stub;
    Class<?> iInterface;

    public BinderHookProxyHandler(IBinder base) {
        this.mBase = base;
        try {
            this.stub = Class.forName("android.content.IClipboard$Stub");
            this.iInterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            mLogger.i("hook method is queryLocalInterface.");
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                    new Class[]{this.iInterface},
                    new BinderHookHandler(this.mBase, this.stub));
        }
        mLogger.i("method: " + method.getName());
        return method.invoke(this.mBase, args);
    }
}
