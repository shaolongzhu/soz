package com.soz.changeSkin.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.soz.changeSkin.listener.IResourcesManager;
import com.soz.log.Logger;

/**
 * Created by zhushaolong on 7/18/16.
 */
public class ResourcesManager implements IResourcesManager {
    Logger mLogger = new Logger("ResourcesManager");
    private static final String DRAWABLE = "build/intermediates/exploded-aar/com.android.support/appcompat-v7/23.4.0/res/drawable";
    private Resources mResources;
    private String mPkgName = SkinConfig.SKIN_PKG_NAME;

    public ResourcesManager(Resources resources) {
        this.mResources = resources;
    }

    @Override
    public Drawable getDrawableByName(String name) {
        Drawable drawable;
        int id = 0;
        try {
            id = mResources.getIdentifier(name, DRAWABLE, mPkgName);
            drawable= mResources.getDrawable(id);
        } catch (Resources.NotFoundException e) {
            mLogger.i("[error id = " + id + "] " + e.getMessage());
            drawable = null;
        }

        return drawable;
    }
}
