package com.soz.sozTest.plugin.proxyHook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.sozTest.act.TargetActivity;
import com.soz.utils.AppManagerUtils;

/**
 * Created by zhushaolong on 9/2/16.
 */
public class ActHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ActHookActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_act_hook);
    }

    public void jumpToTargetActivity(View view) {
        Intent intent = new Intent(ActHookActivity.this, TargetActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try {
            HookHelper.HookActivityManager();
            HookHelper.HookActivityThreadHandler();
        } catch (Throwable throwable) {
            throw new RuntimeException("hook failed", throwable);
        }
    }
}
