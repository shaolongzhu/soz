package com.example.soz.changeSkin.listener;

import com.example.soz.changeSkin.utils.ResourcesManager;

/**
 * change skin interface for external use
 * Created by zhushaolong on 7/18/16.
 */
public interface ISkinManager {
    // return resources manager
    ResourcesManager getResourcesManager();
    // change skin
    void changeSkin(String PluginPkgName);
}
