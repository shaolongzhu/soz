package com.example.soz.changeSkin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.soz.changeSkin.attr.SkinView;
import com.example.soz.changeSkin.listener.ISkinChangeListener;
import com.example.soz.changeSkin.listener.ISkinManager;
import com.example.soz.changeSkin.utils.ResourcesManager;
import com.example.soz.log.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dynamic change skin
 * Created by zhushaolong on 7/18/16.
 */
public class SkinManager implements ISkinManager {
    private Logger mLogger = new Logger("SkinManager");
    private Context mContext;
    private Resources mResources;// for getting res
    private ResourcesManager mResourcesManager;// for resources manager
    private String mPluginPkgPath;// plugin package path
    private List<ISkinChangeListener> mSkinChangeListener = new ArrayList<ISkinChangeListener>();
    private Map<ISkinChangeListener, List<SkinView>> mSkinViewMap = new HashMap<ISkinChangeListener, List<SkinView>>();

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
    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * add skin view for a special interface(activity)
     * @param skinChangeListener
     * @param skinViews
     */
    public void addSkinView(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViewMap.put(skinChangeListener, skinViews);
    }

    /**
     * get a skin view for a special interface(activity)
     * @param skinChangeListener
     * @return
     */
    public List<SkinView> getSkinView(ISkinChangeListener skinChangeListener) {
        return mSkinViewMap.get(skinChangeListener);
    }

    /**
     * load plugin res
     */
    private void loadPlugin() throws Exception{
        mLogger.i("loadPlugin");
        AssetManager assetManager = AssetManager.class.newInstance();
        Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
        method.invoke(assetManager, this.mPluginPkgPath);
        Resources superRes = this.mContext.getResources();
        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourcesManager = new ResourcesManager(this.mResources);
    }

    /**
     * add skin change listener
     * @param skinChangeListener
     */
    public void addSkinChangeListener(ISkinChangeListener skinChangeListener) {
        this.mSkinChangeListener.add(skinChangeListener);
    }

    /**
     * delete skin change listener
     * @param skinChangeListener
     */
    public void removeSkinChangeListener(ISkinChangeListener skinChangeListener) {
        this.mSkinChangeListener.remove(skinChangeListener);
    }

    @Override
    public ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }

    /**
     * check the plugin pkg
     * @param pluginPkgPath
     * @return
     */
    private boolean checkPluginPkg(String pluginPkgPath) {
        if (TextUtils.isEmpty(pluginPkgPath)) return false;
        File file = new File(pluginPkgPath);
        if (!file.exists()) return false;
        return true;
    }

    @Override
    public void changeSkin(String pluginPkgPath) {
        if (!checkPluginPkg(pluginPkgPath)) {
            mLogger.i("changeSkin[check plugin pkg fail]");
            return;
        }
        this.mPluginPkgPath = pluginPkgPath;
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    loadPlugin();
                } catch (Exception e) {
                    mLogger.e("loadPlugin error : " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                mLogger.i("onPostExecute");
                for (ISkinChangeListener skinChangeListener:mSkinChangeListener) {
                    skinChangeListener.updateTheme();
                }
            }
        }.execute();
    }

    @Override
    public void apply(ISkinChangeListener skinChangeListener) {
        List<SkinView> skinViews = mSkinViewMap.get(skinChangeListener);
        for (SkinView skinView:skinViews) {
            skinView.apply();
        }
    }
}
