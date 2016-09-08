package com.soz.hook.proxy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;

import com.soz.hook.HookHelper;
import com.soz.log.Logger;

import java.lang.reflect.Field;

/**
 * Created by zhushaolong on 9/5/16.
 */
public class ActivityThreadHandlerCallback implements Handler.Callback {
    private Logger mLogger = new Logger("ActivityThreadHandlerCallback");
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
        mLogger.i("handleLaunchActivity");
        // 这个对象是 ActivityClientRecord 类型
        Object obj = msg.obj;
        try {
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent raw = (Intent) intentField.get(obj);
            Intent target = raw.getParcelableExtra(AMSHookHandler.EXTRA_TARGET_INTENT);
            raw.setComponent(target.getComponent());
            mLogger.i("[handleLaunchActivity] " + target.toString());

            Field activityInfoField = obj.getClass().getDeclaredField("activityInfo");
            activityInfoField.setAccessible(true);
            ActivityInfo activityInfo = (ActivityInfo) activityInfoField.get(obj);
            activityInfo.applicationInfo.packageName = target.getPackage() == null ? target.getComponent().getPackageName()
                    : target.getPackage();
            mLogger.i("[packageName ]" + activityInfo.applicationInfo.packageName);
            HookHelper.HookPackageManager();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
