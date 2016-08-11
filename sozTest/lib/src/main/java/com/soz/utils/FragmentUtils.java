package com.soz.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * fragment mansger utils
 * Created by zhushaolong on 7/19/16.
 */
public class FragmentUtils {
    private FragmentUtils() {
    }

    public static void addFragment(Activity activity, int containerId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.commit();
    }
}
