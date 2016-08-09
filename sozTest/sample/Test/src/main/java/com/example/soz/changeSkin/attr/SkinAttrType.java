package com.example.soz.changeSkin.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.soz.changeSkin.SkinManager;

/**
 * skin type for changing
 * Created by zhushaolong on 7/22/16.
 */
public enum SkinAttrType {

    BACKGROUND("background")
            {
                @Override
                public void apply(View view, String name) {
                    Drawable drawable = SkinManager.getInstance().getResourcesManager().getDrawableByName(name);
                    if (drawable == null) return;
                    view.setBackground(drawable);
                }
            }, SRC("src")
            {
                public void apply(View view, String name) {
                    if (view instanceof ImageView) {
                        Drawable drawable = SkinManager.getInstance().getResourcesManager().getDrawableByName(name);
                        if (drawable == null) return;
                        ((ImageView) view).setImageDrawable(drawable);
                    }
                }
            };

    private String attrType;

    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String name);
}
