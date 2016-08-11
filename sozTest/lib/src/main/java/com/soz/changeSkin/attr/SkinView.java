package com.soz.changeSkin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by zhushaolong on 7/22/16.
 */
public class SkinView {
    View view;
    List<SkinAttr> skinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.skinAttrs = skinAttrs;
    }

    public void apply() {
        for (SkinAttr skinAttr:skinAttrs) {
            skinAttr.apply(view);
        }
    }
}
