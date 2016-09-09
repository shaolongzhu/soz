package com.soz.utils;

import android.content.Context;

import java.io.File;

import dalvik.system.DexClassLoader;

/**
 * Created by zhushaolong on 9/9/16.
 */
public class ClassLoaderUtils {
    private ClassLoaderUtils() {
    }

    public static ClassLoader getPluginClassLoader(Context context, File apkFile) throws Exception{
        return new DexClassLoader(apkFile.getPath(),
                FileUtils.getPluginOptDexDir(context).getPath(),
                FileUtils.getPluginLibDir(context).getPath(),
                context.getClassLoader());
    }
}
