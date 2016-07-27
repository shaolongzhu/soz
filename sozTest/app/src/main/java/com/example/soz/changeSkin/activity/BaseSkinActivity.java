package com.example.soz.changeSkin.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.soz.activity.BaseActivity;
import com.example.soz.changeSkin.SkinInflaterFactory;
import com.example.soz.changeSkin.SkinManager;
import com.example.soz.changeSkin.listener.ISkinChangeListener;
import com.example.soz.log.Logger;

/**
 * Created by zhushaolong on 7/18/16.
 */
public class BaseSkinActivity extends BaseActivity implements ISkinChangeListener {
    Logger mLogger = new Logger("BaseSkinActivity");
    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
        mSkinInflaterFactory = new SkinInflaterFactory(this);
        this.getLayoutInflater().setFactory(mSkinInflaterFactory);
        SkinManager.getInstance().addSkinChangeListener(this);
    }

    @Override
    public void onRestart() {
        mLogger.i("onRestart");
        super.onRestart();
    }

    @Override
    public void onStart() {
        mLogger.i("onStart");
        super.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        mLogger.i("onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mLogger.i("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
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
    public void onSaveInstanceState(Bundle outState) {
        mLogger.i("onSavedInstanceState");
        super.onSaveInstanceState(outState);
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
        SkinManager.getInstance().removeSkinChangeListener(this);
    }

    @Override
    public void updateTheme() {
        mLogger.i("updateTheme");
    }
}
