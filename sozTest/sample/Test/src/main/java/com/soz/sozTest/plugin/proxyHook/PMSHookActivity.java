package com.soz.sozTest.plugin.proxyHook;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;

/**
 * Created by zhushaolong on 9/1/16.
 */
public class PMSHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("PMSHookActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_pms_hook);
    }

    public void installApplication(View view) {
        this.getPackageManager().getInstalledApplications(0);
    }

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try {
            HookHelper.HookPackageManager(newBase);
        } catch (Exception e) {
            mLogger.i("[PMS] hook failed");
            e.printStackTrace();
        }
    }
}
