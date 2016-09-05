package com.soz.sozTest.act;

import android.os.Bundle;

import com.soz.activity.BaseActivity;
import com.soz.log.Logger;
import com.soz.sozTest.R;

/**
 * Created by zhushaolong on 9/2/16.
 */
public class TargetActivity extends BaseActivity {
    Logger mLogger = new Logger("TargetActivity");

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_target);
    }
}
