package com.soz.sozTest.plugin.proxyHook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;

/**
 * Created by zhushaolong on 9/1/16.
 */
public class AMSHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("AMSHookActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_ams_hook);
    }

    public void startActivityProxy(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://www.baidu.com");
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try {
            HookHelper.HookActivityManager();
        } catch (Exception e) {
            mLogger.i("hook fail [hookActivityManager]");
            e.printStackTrace();
        }
    }
}
