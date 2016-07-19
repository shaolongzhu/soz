package com.example.soz.changeSkin.utils;

import android.content.res.Resources;

import com.example.soz.changeSkin.listener.IResourcesManager;

/**
 * Created by zhushaolong on 7/18/16.
 */
public class ResourcesManager implements IResourcesManager {
    private Resources mResources;

    public ResourcesManager(Resources resources) {
        this.mResources = resources;
    }

    @Override
    public int getColor(String name) {
        return 0;
    }

    @Override
    public int getDrawable(String name) {
        return 0;
    }
}
