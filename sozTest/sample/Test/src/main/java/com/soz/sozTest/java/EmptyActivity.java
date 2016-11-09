package com.soz.sozTest.java;

import android.os.Bundle;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;

/**
 * Created by zhushaolong on 11/9/16.
 */

public class EmptyActivity extends BaseActivity {
    private Logger mLogger = new Logger("EmptyActivity");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogger.i("onCreate");
        Child1BaseClass child1BaseClass = new Child1BaseClass();
        System.out.println(child1BaseClass.i);
    }
}
