package com.example.zhushaolong.soztest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.soz.activity.BaseActivity;
import com.example.soz.utils.AppManagerUtils;
import com.example.zhushaolong.soztest.launchModeTest.TestOneActivity;
import com.example.zhushaolong.soztest.pluginTest.ChangeSkinActivity;

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
}
