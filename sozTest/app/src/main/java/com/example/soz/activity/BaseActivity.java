package com.example.soz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.soz.log.Logger;

/**
 * the base activity, all activity should extend the class
 * Created by zhushaolong on 7/14/16.
 */
public class BaseActivity extends Activity {
    private Logger mLogger = new Logger("BaseActivity");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
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
        mLogger.i("RestoreInstanceState");
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
        mLogger.i("onSaveInstanceState");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mLogger.i("onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mLogger.i("onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }
}
