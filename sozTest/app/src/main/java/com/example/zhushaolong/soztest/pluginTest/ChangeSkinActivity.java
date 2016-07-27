package com.example.zhushaolong.soztest.pluginTest;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.soz.changeSkin.SkinManager;
import com.example.soz.changeSkin.activity.BaseSkinActivity;
import com.example.soz.changeSkin.utils.SkinConfig;
import com.example.soz.log.Logger;
import com.example.soz.recyclerView.adapter.BaseCustomAdapter;
import com.example.soz.recyclerView.utils.LayoutManagerType;
import com.example.soz.recyclerView.utils.RecyclerViewManager;
import com.example.soz.utils.FragmentUtils;
import com.example.zhushaolong.soztest.R;
import com.nineoldandroids.view.ViewHelper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * test activity for changing skin
 * Created by zhushaolong on 7/19/16.
 */
public class ChangeSkinActivity extends BaseSkinActivity {
    private Logger mLogger = new Logger("ChangeSkinActivity");
    String[] data = new String[]{"item1", "item2", "item3"};
    List<String> mData = Arrays.asList(data);
    DrawerLayout mDrawerLayout;
    FrameLayout mContent;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        mLogger.i("onCrete");
        super.onCreate(onSavedInstanceState);
        this.setContentView(R.layout.act_plugin_skin);
        this.initView();
        this.initData();
        this.initEvent();
    }

    /**
     * init view
     */
    private void initView() {
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_container);
        mContent = (FrameLayout) this.findViewById(R.id.drawer_content);
        mRecyclerView = (RecyclerView) this.findViewById(R.id.drawer_menu);
    }

    /**
     * init data
     */
    private void initData() {
        BaseCustomAdapter adapter = new BaseCustomAdapter(mData);
        mRecyclerView.setAdapter(adapter);
        RecyclerViewManager.setRecyclerViewLayoutManager(this, mRecyclerView, LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        FragmentUtils.addFragment(this, R.id.drawer_content, new ChangeSkinFragment());
    }

    /**
     * init event
     */
    private void initEvent() {
        this.mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mLogger.i("slideOffset = " + slideOffset);
                if ("LEFT".equals(drawerView.getTag())) {
                    leftSlide(drawerView, slideOffset);
                } else {
                    rightSlide(drawerView, slideOffset);
                }
            }
        });
    }

    /**
     * left menu slide
     * @param view
     * @param slideOffset
     */
    private void leftSlide(View view, float slideOffset) {
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;
        float leftScale = 1 - 0.3f * scale;
        ViewHelper.setScaleX(view, leftScale);
        ViewHelper.setScaleY(view, leftScale);
        ViewHelper.setAlpha(view, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(mContent,
                view.getMeasuredWidth() * (1 - scale));
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent,
                mContent.getMeasuredHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);
    }

    /**
     * right menu slide
     * @param view
     * @param slideOffset
     */
    private void rightSlide(View view, float slideOffset) {
        float scale = 1 - slideOffset;
        float rightScale = 0.8f + scale * 0.2f;
        ViewHelper.setTranslationX(mContent,
                -view.getMeasuredWidth() * slideOffset);
        ViewHelper.setPivotX(mContent,mContent.getMeasuredWidth());
        ViewHelper.setPivotY(mContent,
                mContent.getMeasuredHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mLogger.i("onCreateOptionsMenu");
        this.getMenuInflater().inflate(R.menu.skin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        mLogger.i("onOptionsItemSelected");
        switch (menuItem.getItemId()) {
            case R.id.skin_menu_1:
                mLogger.i("SKIN_PKG_PATH: " + SkinConfig.SKIN_PKG_PATH);
                SkinManager.getInstance().changeSkin(SkinConfig.SKIN_PKG_PATH);
                break;
            case R.id.skin_menu_2:
                mLogger.i("SKIN_PKG_PATH: " + SkinConfig.SKIN_PKG_PATH);
                changeBackground();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void updateTheme() {
        mLogger.i("updateTheme");
        SkinManager.getInstance().apply(this);
    }

    /**
     * change bg
     */
    private void changeBackground() {
        AssetManager assetManager = null;
        try
        {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, SkinConfig.SKIN_PKG_PATH);

            File file = new File(SkinConfig.SKIN_PKG_PATH);
            mLogger.e(file.exists()+"");
            Resources superRes = getResources();
            Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

            int mainBgId = mResources.getIdentifier("skin_main_bg", "drawable", SkinConfig.SKIN_PKG_NAME);
            findViewById(R.id.drawer_menu).setBackgroundDrawable(mResources.getDrawable(mainBgId));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
