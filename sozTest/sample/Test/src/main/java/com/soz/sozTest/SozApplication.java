package com.soz.sozTest;

import android.app.Application;

import com.soz.changeSkin.SkinManager;

/**
 * Created by zhushaolong on 7/22/16.
 */
public class SozApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        this.init();
    }

    /**
     * init
     */
    private void init() {
        SkinManager.getInstance().init(this);
    }
}
