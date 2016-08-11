package com.soz.sozTest.plugin;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soz.dynamicLoad.DLBasePluginActivity;
import com.soz.dynamicLoad.internal.DLPluginManager;
import com.soz.log.Logger;
import com.soz.recyclerView.adapter.BaseCustomAdapter;
import com.soz.recyclerView.utils.LayoutManagerType;
import com.soz.recyclerView.utils.RecyclerViewManager;
import com.soz.recyclerView.viewHolder.BaseCustomItem;
import com.soz.sozTest.R;
import com.soz.utils.AppManagerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DL plugin for test
 * Created by zhushaolong on 8/8/16.
 */
public class DynamicLoadActivity extends DLBasePluginActivity implements BaseCustomAdapter.onItemClickListener {
    Logger mLogger = new Logger("DynamicLoadActivity");
    private final String pluginPath = Environment.getExternalStorageDirectory()
            + File.separator + "dl";
    RecyclerView mListContainer;
    List<BaseCustomItem> mData = new ArrayList<BaseCustomItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mLogger.i("onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.act_plugin_dl);
        this.initView();
        this.initData();
    }

    private void initView() {
        this.mListContainer = (RecyclerView) this.findViewById(R.id.dl_plugin_list);
    }

    private void initData() {
        File file = new File(pluginPath);
        File[] plugins = file.listFiles();
        if (plugins == null || plugins.length == 0) return;
        for (File f: plugins) {
            DLPluginManager.getInstance(this).loadAPK(f.getAbsolutePath());
            BaseCustomItem item = new BaseCustomItem();
            item.setTitle(AppManagerUtils.getAppLabel(this, f.getAbsolutePath()).toString());
            item.setIcon(AppManagerUtils.getAppIcon(this, f.getAbsolutePath().toString()));
            mData.add(item);
        }
        RecyclerViewManager.setRecyclerViewLayoutManager(this, this.mListContainer, LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        BaseCustomAdapter adapter = new BaseCustomAdapter(mData, this);
        this.mListContainer.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
    }
}
