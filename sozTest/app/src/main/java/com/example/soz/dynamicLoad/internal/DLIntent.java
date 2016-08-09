package com.example.soz.dynamicLoad.internal;

import android.content.Intent;

/**
 * Created by zhushaolong on 8/9/16.
 */
public class DLIntent extends Intent{
    private String pluginPackage;
    private String pluginClass;

    public DLIntent(String pluginPackage) {
        this(pluginPackage, null);
    }

    public DLIntent(String pluginPackage, String pluginClass) {
        this.init(pluginPackage, pluginClass);
    }

    private void init (String pluginPackage, String pluginClass) {
        this.pluginPackage = pluginPackage;
        this.pluginClass = pluginClass;
    }

    public String getPluginPackage() {
        return this.pluginPackage;
    }

    public void setPluginPackage(String pluginPackage) {
        this.pluginPackage = pluginPackage;
    }

    public String getPluginClass() {
        return this.pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }
}
