package com.example.soz.changeSkin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.soz.changeSkin.attr.SkinAttr;
import com.example.soz.changeSkin.attr.SkinAttrTypeSupport;
import com.example.soz.changeSkin.attr.SkinView;
import com.example.soz.changeSkin.listener.ISkinChangeListener;
import com.example.soz.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class SkinInflaterFactory implements LayoutInflater.Factory {
    Logger mLogger = new Logger("SkinInflaterFactory");
    private final ISkinChangeListener mSkinChangeListener;

    public SkinInflaterFactory(ISkinChangeListener skinChangeListener) {
        this.mSkinChangeListener = skinChangeListener;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        mLogger.i("onCreateView: name = " + name);
        View view = createView(context, name, attrs);
        if (view == null) {
            return null;
        }
        parseAttrs(view, context, attrs);
        return view;
    }

    /**
     * create view
     * @param context
     * @param attrs
     * @return
     */
    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf(".")) {
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (ClassNotFoundException e) {
            mLogger.e("error while create [" + name + "] : " + e.getMessage());
            view = null;
        }
        return view;
    }

    /**
     * parse attrs
     * @param context
     * @param attrs
     */
    private void parseAttrs(View view, Context context, AttributeSet attrs) {
        List<SkinAttr> attrList = SkinAttrTypeSupport.getSkinAttr(attrs, context);
        if (attrList.size() != 0) {
            SkinView skinView = new SkinView(view, attrList);
            List<SkinView> skinViews = SkinManager.getInstance().getSkinView(this.mSkinChangeListener);
            if (skinViews == null) {
                skinViews = new ArrayList<SkinView>();
                SkinManager.getInstance().addSkinView(this.mSkinChangeListener, skinViews);
            }
            skinViews.add(skinView);
        }
    }
}
