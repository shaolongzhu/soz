package com.example.soz.recyclerView.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soz.log.Logger;
import com.example.soz.recyclerView.viewHolder.BaseCustomViewHolder;
import com.example.zhushaolong.soztest.R;

import java.util.List;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class BaseCustomAdapter extends RecyclerView.Adapter<BaseCustomViewHolder> {
    Logger mLogger = new Logger("BaseCustomAdapter");
    List<String> mData;

    public BaseCustomAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public BaseCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new BaseCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseCustomViewHolder holder, int position) {
        mLogger.i("position = " + position);
        holder.setTitle(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
