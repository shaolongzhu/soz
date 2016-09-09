package com.soz.sozTest.plugin.proxyHook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.utils.ConstantUtils;
import com.soz.utils.FileUtils;

import java.io.File;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class ReceiverHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ReceiverHookActivity");

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        FileUtils.extractAssets(newBase, ConstantUtils.APK_FILE_2);
        try {
            File apkFile = newBase.getFileStreamPath(ConstantUtils.APK_FILE_2);
            HookHelper.loadReceiver(newBase, apkFile);
        } catch (Exception e) {
            mLogger.i("hook receiver failed");
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_receiver_hook);
    }

    public void sendBroadcastToPlugin(View view) {
        mLogger.i("sendBroadcastToPlugin");
        this.sendBroadcast(new Intent(ConstantUtils.PLUGIN_ACTION_RECEIVER_1));
    }
}
