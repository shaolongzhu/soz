package com.soz.sozTest.plugin.proxyHook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.soz.hook.BaseHookPluginActivity;
import com.soz.hook.HookHelper;
import com.soz.log.Logger;
import com.soz.sozTest.R;
import com.soz.utils.AppManagerUtils;
import com.soz.utils.ConstantType;
import com.soz.utils.ConstantUtils;
import com.soz.utils.FileUtils;

import java.io.File;

/**
 * Created by zhushaolong on 9/5/16.
 */
public class ClassLoaderHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ClassLoaderHookActivity");
    private int mClassLoaderType = ConstantType.CLASSLOADER_PATCH;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_classloader_hook);
        this.acquireIntentData();
        this.initEvent();
        if (this.mClassLoaderType == ConstantType.CLASSLOADER_PATCH) {
            loadApkPatch();
        } else {
            // TODO:need to add other method
            loadApkPatch();
        }
    }

    private void acquireIntentData() {
        mLogger.i("[acquireIntentData] " + this.getIntent().toString());
        this.mClassLoaderType = this.getIntent().getIntExtra(ConstantUtils.CLASSLOADER_TYPE, ConstantType.CLASSLOADER_PATCH);
    }

    private void initEvent() {
        TextView textView = (TextView) this.findViewById(R.id.classloader_hook_btn);
        if (this.mClassLoaderType == ConstantType.CLASSLOADER_PATCH) {
            mLogger.i("initEvent[patch]");
            textView.setText("patch");
        } else {
            mLogger.i("initEvent[custom]");
            textView.setText("custom");
        }
    }

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        FileUtils.extractAssets(newBase, ConstantUtils.APK_FILE_2);
    }

    private void loadApkPatch() {
        try {
            File apkFile = this.getFileStreamPath(ConstantUtils.APK_FILE_2);
            File dexFile = this.getFileStreamPath(ConstantUtils.DEX_FILE_2);
            HookHelper.PatchClassLoader(this.getClassLoader(),apkFile, dexFile);
            HookHelper.HookActivityManager();
            HookHelper.HookActivityThreadHandler();
        } catch (Exception e) {
            mLogger.i("loadApkPatch failed");
        }
    }

    public void jumpToPlugin(View view) {
        mLogger.i("jumpToPlugin");
        Intent intent =new Intent();
        intent.setComponent(new ComponentName(ConstantUtils.PACKAGE_NAME_2, ConstantUtils.PLUGIN_ACTIVITY_2));
        AppManagerUtils.startActivity(this, intent);
    }
}
