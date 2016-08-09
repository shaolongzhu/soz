package com.example.soz.recyclerView.viewHolder;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by zhushaolong on 8/8/16.
 */
public class BaseCustomItem implements Serializable{
    private static final long serialVersionUID = -1360497120250829863L;

    private String title;
    private String desc;
    private Drawable icon;
    private int iconId;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
