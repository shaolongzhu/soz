package com.soz.dynamicLoad;

import android.os.Bundle;

/**
 * activity life-cycle
 * Created by zhushaolong on 8/8/16.
 */
public interface DLPlugin {
    void onCreate(Bundle savedInstanceState);
    void onRestart();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
