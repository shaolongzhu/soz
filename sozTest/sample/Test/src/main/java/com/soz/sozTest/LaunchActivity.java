package com.soz.sozTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;
import com.soz.sozTest.broadcast.Broadcast1Activity;
import com.soz.sozTest.java.EmptyActivity;
import com.soz.sozTest.launchMode.TestOneActivity;
import com.soz.sozTest.plugin.ChangeSkinActivity;
import com.soz.sozTest.plugin.DynamicLoadActivity;
import com.soz.sozTest.plugin.MainHookActivity;
import com.soz.utils.AppManagerUtils;

public class LaunchActivity extends BaseActivity {
    Logger mLogger = new Logger("LaunchActivity");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launch_main);
    }

    public void runApp(View view) {
        AppManagerUtils.runApp(this, "com.letv.tv");
    }

    public void jumpToLaunchMode(View view) {
        Intent intent = new Intent(LaunchActivity.this, TestOneActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToChangeSkin(View view) {
        Intent intent = new Intent(LaunchActivity.this, ChangeSkinActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToDLPlugin(View view) {
        Intent intent = new Intent(LaunchActivity.this, DynamicLoadActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToHookMain(View view) {
        Intent intent = new Intent(LaunchActivity.this, MainHookActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToTest(View view) {
        Intent intent = new Intent(LaunchActivity.this, EmptyActivity.class);
        AppManagerUtils.startActivity(this, intent);
    }

    public void jumpToBroadcast(View view) {
        mLogger.i("jumpToBroadcast");
        Intent intent = new Intent(LaunchActivity.this, Broadcast1Activity.class);
        AppManagerUtils.startActivity(this, intent);
    }
}
