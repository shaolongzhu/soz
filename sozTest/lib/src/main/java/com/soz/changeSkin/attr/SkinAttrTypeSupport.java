package com.soz.changeSkin.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.soz.changeSkin.utils.SkinConfig;
import com.soz.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * provide method for type support test
 * Created by zhushaolong on 7/22/16.
 */
public class SkinAttrTypeSupport {
    private static Logger mLogger = new Logger("SkinAttrTypeSupport");
    private SkinAttrTypeSupport() {
    }

    /**
     * get skin attr for a view
     * @param attrs
     * @param context
     * @return
     */
    public static List<SkinAttr> getSkinAttr(AttributeSet attrs, Context context) {
        List<SkinAttr> attrList = new ArrayList<SkinAttr>();
        for (int i = 0; i < attrs.getAttributeCount(); ++i) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            SkinAttrType attrType = getSkinAttrType(name);
            if (attrType == null) {
                continue;
            }
            if (value.startsWith("@")) {
                int id = Integer.parseInt(value.substring(1));
                String entryName = context.getResources().getResourceEntryName(id);
                mLogger.i("entry name = " + entryName + ",attr name = " + name);
                if (entryName.startsWith(SkinConfig.SKIN_PREFIX)) {
                    SkinAttr skinAttr = new SkinAttr(entryName, attrType);
                    attrList.add(skinAttr);
                }
            }
        }

        return attrList;
    }

    /**
     * get the support skin attr name
     * @param name
     * @return
     */
     private static SkinAttrType getSkinAttrType(String name) {
        for (SkinAttrType attrType:SkinAttrType.values()) {
            if (attrType.getAttrType().equals(name)) {
                return attrType;
            }
        }
        return null;
    }
}
