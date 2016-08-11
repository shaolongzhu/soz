package com.soz.recyclerView.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * recycerView static utils
 * Created by zhushaolong on 7/19/16.
 */
public class RecyclerViewManager {
    private static final int SPAN_COUNT = 2;

    private RecyclerViewManager() {
    }

    public static void setRecyclerViewLayoutManager(Context context, RecyclerView recyclerView,
                                                    LayoutManagerType layoutManagerType) {
        RecyclerView.LayoutManager layoutManager;
        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                layoutManager = new LinearLayoutManager(context);
                break;
            case GRIDE_LAYOUT_MANAGER:
                layoutManager = new GridLayoutManager(context, SPAN_COUNT);
                break;
            default:
                layoutManager = new LinearLayoutManager(context);
                break;
        }
        recyclerView.setLayoutManager(layoutManager);
    }
}
