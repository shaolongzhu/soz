package com.soz.sozTest.plugin.proxyHook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
 * Created by zhushaolong on 9/12/16.
 */
public class ProviderHookActivity extends BaseHookPluginActivity {
    Logger mLogger = new Logger("ProviderHookActivity");

    private Uri URI = Uri.parse("content://" + ConstantUtils.PROVIDER_AUTHORITY);
    private static int count = 0;

    @Override
    public void attachBaseContext(Context newBase) {
        mLogger.i("attachBaseContext");
        super.attachBaseContext(newBase);
        try {
            File apkFile = this.getFileStreamPath(ConstantUtils.APK_FILE_2);
            if (!apkFile.exists()) {
                FileUtils.extractAssets(newBase, ConstantUtils.APK_FILE_2);
            }
            File dexFile = this.getFileStreamPath(ConstantUtils.DEX_FILE_2);
            HookHelper.PatchClassLoader(newBase.getClassLoader(), apkFile, dexFile);
            HookHelper.loadProviders(newBase, apkFile);
        } catch (Exception e) {
            mLogger.i("hook failed");
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_provider_hook);
    }

    public void providerInsert(View view) {
        ContentValues values = new ContentValues();
        values.put("name", "name" + count++);
        Uri uri = getContentResolver().insert(URI, values);
        mLogger.i("providerInsert[" + "uri = " + uri.toString() + ", name = "+ values.get("name"));
    }

    public void providerQuery(View view) {
        mLogger.i("providerQuery");
        Cursor c = getContentResolver().query(URI, null, null, null, null);
        assert c != null;
        while (c.moveToNext()) {
            int num = c.getColumnCount();
            mLogger.i("num = " + num);
            StringBuilder sb = new StringBuilder("columns:");
            for (int i = 0; i < num; ++i) {
                sb.append(c.getString(i) + ", ");
            }
            mLogger.i("query " + sb.toString());
        }
    }
}
