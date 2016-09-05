package com.soz.hook.proxy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by zhushaolong on 9/5/16.
 */
public class ActivityThreadHandlerCallback implements Handler.Callback {
    private Handler mBase;

    public ActivityThreadHandlerCallback(Handler base) {
        this.mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 100:
                handleLaunchActivity(msg);
                break;
        }
        this.mBase.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        Object obj = msg.obj;
        try {
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent raw = (Intent) intentField.get(obj);
            Intent target = raw.getParcelableExtra(AMSHookHandler.EXTRA_TARGET_INTENT);
            raw.setComponent(target.getComponent());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
