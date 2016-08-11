package com.soz.changeSkin.listener;

import com.soz.changeSkin.utils.ResourcesManager;

/**
 * change skin interface for external use
 * Created by zhushaolong on 7/18/16.
 */
public interface ISkinManager {
    // return resources manager
    ResourcesManager getResourcesManager();
    // loading resource for changing skin
    void changeSkin(String PluginPkgPath);
    // change skin
    void apply(ISkinChangeListener skinChangeListener);
}
