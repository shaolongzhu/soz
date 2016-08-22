package com.soz.sozTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soz.activity.BaseActivity;
import com.soz.sozTest.plugin.MainHookActivity;
import com.soz.utils.AppManagerUtils;
import com.soz.sozTest.launchMode.TestOneActivity;
import com.soz.sozTest.plugin.ChangeSkinActivity;
import com.soz.sozTest.plugin.DynamicLoadActivity;

public class LaunchActivity extends BaseActivity {

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
}
