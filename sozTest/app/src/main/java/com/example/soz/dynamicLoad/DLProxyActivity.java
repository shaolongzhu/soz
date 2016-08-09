package com.example.soz.dynamicLoad;

import android.os.Bundle;

import com.example.soz.activity.BaseActivity;
import com.example.soz.dynamicLoad.internal.DLAttachable;
import com.example.soz.dynamicLoad.internal.DLProxyImpl;
import com.example.soz.log.Logger;

/**
 * activity proxy for handling plugin activity
 * Created by zhushaolong on 8/8/16.
 */
public class DLProxyActivity extends BaseActivity implements DLAttachable {
    Logger mLogger = new Logger("DLProxyActivity");
    DLPlugin mRemoteActivity;
    DLProxyImpl impl = new DLProxyImpl(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
        impl.onCreate(this.getIntent());
    }

    @Override
    public void onAttach(DLPlugin dlPlugin) {
        this.mRemoteActivity = dlPlugin;
    }

    @Override
    public void onRestart() {
        mLogger.i("onRestart");
        super.onRestart();
        mRemoteActivity.onRestart();
    }

    @Override
    public void onStart() {
        mLogger.i("onStart");
        super.onStart();
        mRemoteActivity.onStart();
    }

    @Override
    public void onResume() {
        mLogger.i("onResume");
        super.onResume();
        mRemoteActivity.onResume();
    }

    @Override
    public void onPause() {
        mLogger.i("onPause");
        super.onPause();
        mRemoteActivity.onPause();
    }

    @Override
    public void onStop() {
        mLogger.i("onStop");
        super.onStop();
        mRemoteActivity.onStop();
    }

    @Override
    public void onDestroy() {
        mLogger.i("onDestroy");
        super.onDestroy();
        mRemoteActivity.onDestroy();
    }
}
