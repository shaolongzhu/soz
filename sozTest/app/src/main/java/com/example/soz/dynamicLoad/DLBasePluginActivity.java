package com.example.soz.dynamicLoad;

import com.example.soz.activity.BaseActivity;
import com.example.soz.log.Logger;

/**
 * plugin base activity, all plugin activity should extend it.
 * Created by zhushaolong on 8/8/16.
 */
public abstract class DLBasePluginActivity extends BaseActivity{
    Logger mLogger = new Logger("DLBasePluginActivity");
}
