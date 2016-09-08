package com.soz.sozPlugin;

import android.os.Bundle;
import android.widget.Toast;

import com.soz.dynamicLoad.DLBasePluginActivity;
import com.soz.log.Logger;

/**
 * dynamic loader for plugin test
 * Created by zhushaolong on 8/11/16.
 */
public class DLPluginActivity extends DLBasePluginActivity{
    Logger mLogger = new Logger("DLPluginActivity");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_dl);
        Toast.makeText(this, "other", Toast.LENGTH_LONG).show();
    }
}
