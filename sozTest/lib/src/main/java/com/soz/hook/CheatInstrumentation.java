package com.soz.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.soz.log.Logger;

import java.lang.reflect.Method;

/**
 * Created by zhushaolong on 8/19/16.
 */
public class CheatInstrumentation extends Instrumentation {
    Logger mLogger = new Logger("CheatInstrumentation");
    Instrumentation mBase;

    public CheatInstrumentation(Instrumentation instrumentation) {
        this.mBase = instrumentation;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        mLogger.i("执行到了execStartActivity，参数如下：\n" + "who = [" + who + "]"
                + "\ncontextThread = [" + contextThread + "]"
                + "\ntoken = [" + "]" + "\ntarget = [" + target + "]"
                + "\nrequestCode = [" + requestCode + "]" + "\noptions = [" + options + "]");
        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class,
                    IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who, contextThread, token, target,
                    intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException("do not support, pls adapt it");
        }
    }
}
