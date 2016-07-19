package com.example.soz.changeSkin;

import android.content.res.Resources;

import com.example.soz.changeSkin.listener.ISkinManager;
import com.example.soz.changeSkin.utils.ResourcesManager;

/**
 * dynamic change skin
 * Created by zhushaolong on 7/18/16.
 */
public class SkinManager implements ISkinManager {
    private Resources mResources;// for getting res
    private ResourcesManager mResourcesManager;// for resources manager

    private SkinManager() {
    }

    /**
     * get singleton
     * @return
     */
    public static SkinManager getInstance() {
        return SkinManagerHolder.INSTANCE;
    }

    private static class SkinManagerHolder {
        public final static SkinManager INSTANCE = new SkinManager();
    }

    /**
     * init resource
     */
    private void init() {}

    /**
     * load plugin res
     */
    private void loadPlugin() {
    }

    @Override
    public ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }

    @Override
    public void changeSkin(String pluginPkgName) {}
}
