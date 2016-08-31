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
 * hook proxy
 * Created by zhushaolong on 8/19/16.
 */
public class ProxyHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ProxyHookActivity");

    @Override
    public void onCreate(Bundle onSavedInstanced) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanced);
        this.setContentView(R.layout.act_proxy_hook);
    }

    public void jumpToOther(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("http://www.baidu.com"));
        this.getApplicationContext().startActivity(intent);
    }

    @Override
    public void onResume() {
        mLogger.i("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        mLogger.i("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        mLogger.i("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mLogger.i("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try{
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
            mLogger.e("hook exception [attachContext]");
        }
    }
}
