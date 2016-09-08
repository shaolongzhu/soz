package com.soz.sozTest.plugin;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.soz.dynamicLoad.DLBasePluginActivity;
import com.soz.dynamicLoad.internal.DLIntent;
import com.soz.dynamicLoad.internal.DLPluginManager;
import com.soz.log.Logger;
import com.soz.recyclerView.adapter.BaseCustomAdapter;
import com.soz.recyclerView.utils.LayoutManagerType;
import com.soz.recyclerView.utils.RecyclerViewManager;
import com.soz.recyclerView.viewHolder.BaseCustomItem;
import com.soz.sozTest.R;
import com.soz.sozTest.plugin.bean.PluginItem;
import com.soz.utils.AppManagerUtils;
import com.soz.utils.ConstantUtils;
import com.soz.utils.FileUtils;

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
    TextView mText;
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
        this.mText = (TextView) this.findViewById(R.id.dl_no_data);
    }

    private void initData() {
        FileUtils.extractAssets(this, ConstantUtils.APK_FILE_2);
//        File file = new File(pluginPath);
        File file = this.getFilesDir();
        File[] plugins = file.listFiles();
        if (plugins == null || plugins.length == 0) {
            mLogger.i("no plugin data");
            this.mText.setVisibility(View.VISIBLE);
            return;
        }
        for (File f: plugins) {
            DLPluginManager.getInstance(this).loadAPK(f.getAbsolutePath());
            PluginItem item = new PluginItem();
            item.setTitle(AppManagerUtils.getAppLabel(this, f.getAbsolutePath()).toString());
            item.setIcon(AppManagerUtils.getAppIcon(this, f.getAbsolutePath().toString()));
            item.setPackageName(AppManagerUtils.getPackageName(this, f.getAbsolutePath().toString()));
            PackageInfo packageInfo = AppManagerUtils.getPackageInfo(this, f.getAbsolutePath());
            if (packageInfo != null && packageInfo.activities != null &&
                    packageInfo.activities.length >= 2) {
                String launcherActivity = packageInfo.activities[1].name;
                item.setLauncherActivity(launcherActivity);
                mLogger.i("launcherActivity name: " + launcherActivity);
            }
            mData.add(item);
        }
        RecyclerViewManager.setRecyclerViewLayoutManager(this, this.mListContainer, LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        BaseCustomAdapter adapter = new BaseCustomAdapter(mData, this);
        this.mListContainer.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mData == null || mData.size() <= position) return;
        String packageName = ((PluginItem) this.mData.get(position)).getPackageName();
        String launcherActivity = ((PluginItem) this.mData.get(position)).getLauncherActivity();
        mLogger.i("[onItemClick] position = " + position + " packageName = " + packageName + " launcherActivity = " + launcherActivity);
        DLPluginManager.getInstance(this)
                .startPluginActivity(this, new DLIntent(packageName, launcherActivity));
    }
}
