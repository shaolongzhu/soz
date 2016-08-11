package com.soz.changeSkin.attr;

import android.view.View;

/**
 * Created by zhushaolong on 7/22/16.
 */
public class SkinAttr {
    String resName;
    SkinAttrType attrType;

    public SkinAttr(String resName, SkinAttrType attrType) {
        this.resName = resName;
        this.attrType = attrType;
    }

    public String getResName() {
        return this.resName;
    }

    public SkinAttrType getAttrType() {
        return this.attrType;
    }

    public void apply(View view) {
        attrType.apply(view, resName);
    }
}
