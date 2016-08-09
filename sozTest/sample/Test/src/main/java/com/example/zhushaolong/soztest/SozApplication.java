package com.example.zhushaolong.soztest;

import android.app.Application;

import com.example.soz.changeSkin.SkinManager;

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
