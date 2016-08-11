package com.soz.sozTest.plugin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soz.fragment.BaseFragment;
import com.soz.log.Logger;
import com.soz.sozTest.R;

/**
 * drawer content
 * Created by zhushaolong on 7/19/16.
 */
public class ChangeSkinFragment extends BaseFragment {
    private Logger mLogger = new Logger("ChangeSkinFragment");
    View mViewContainer;

    @Override
    public void onAttach(Context context) {
        mLogger.i("onAttach");
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewContainer = inflater.inflate(R.layout.plugin_skin_frag, null);
        return mViewContainer;
    }
}
