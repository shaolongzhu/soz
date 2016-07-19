package com.example.soz.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * app manager tools
 * Created by zhushaolong on 6/28/16.
 */
public class AppManagerUtils {

    private AppManagerUtils() {
    }

    public static void runApp(Context context, String paramString) {
        PackageManager localPackageManager = context.getPackageManager();
        try {
            PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramString, 0);
            Intent localIntent1 = new Intent("android.intent.action.MAIN", null);
            localIntent1.setPackage(localPackageInfo.packageName);
            ResolveInfo localResolveInfo = (ResolveInfo)localPackageManager.queryIntentActivities(localIntent1, 0).iterator().next();
            if (localResolveInfo != null)
            {
                String str1 = localResolveInfo.activityInfo.packageName;
                String str2 = localResolveInfo.activityInfo.name;
                Intent localIntent2 = new Intent("android.intent.action.MAIN");
                localIntent2.setComponent(new ComponentName(str1, str2));
                context.startActivity(localIntent2);
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            localNameNotFoundException.printStackTrace();
        }
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
