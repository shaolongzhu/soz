package com.soz.sozTest.java;

import com.soz.log.Logger;

/**
 * Created by zhushaolong on 11/9/16.
 */

public class Child1BaseClass extends BaseClass {
    private Logger mLogger = new Logger("Child1BaseClass");
    public int i = 0;

    public Child1BaseClass() {
        if (mLogger == null) {
            mLogger = new Logger("Child1BaseClass");
            mLogger.i("[Child1BaseClass()]mLogger == null");
        } else {
            mLogger.i("[Child1BaseClass()]mLogger != null");
        }
        mLogger.i("Child1BaseClass");
    }

    @Override
    public void init() {
        if (mLogger == null) {
            mLogger = new Logger("Child1BaseClass");
            mLogger.i("[init()]mLogger == null");
        }
        mLogger.i("init");
        i = 1;
    }
}
