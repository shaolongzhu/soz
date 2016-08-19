package com.soz.sozTest.plugin.bean;

import com.soz.recyclerView.viewHolder.BaseCustomItem;

/**
 * plugin item for dl test
 * Created by zhushaolong on 8/12/16.
 */
public class PluginItem extends BaseCustomItem {
    private String packageName;
    private String launcherActivity;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setLauncherActivity(String launcherActivity) {
        this.launcherActivity = launcherActivity;
    }

    public String getLauncherActivity() {
        return this.launcherActivity;
    }
}
