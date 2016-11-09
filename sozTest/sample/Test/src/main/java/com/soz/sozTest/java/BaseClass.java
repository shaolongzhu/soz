package com.soz.sozTest.java;

import com.soz.log.Logger;

/**
 * Created by zhushaolong on 11/9/16.
 */

public abstract class BaseClass {
    private Logger mLogger = new Logger("BaseClass");

    public BaseClass() {
        mLogger.i("BaseClass");
        init();
    }

    void init() {
        mLogger.i("init");
    }
}
