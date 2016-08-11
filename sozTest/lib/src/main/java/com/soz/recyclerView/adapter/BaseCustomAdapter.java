package com.soz.recyclerView.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soz.lib.R;
import com.soz.log.Logger;
import com.soz.recyclerView.viewHolder.BaseCustomItem;
import com.soz.recyclerView.viewHolder.BaseCustomViewHolder;

import java.util.List;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class BaseCustomAdapter extends RecyclerView.Adapter<BaseCustomViewHolder>  {
    Logger mLogger = new Logger("BaseCustomAdapter");
    List<BaseCustomItem> mData;
    private onItemClickListener mListener;

    public BaseCustomAdapter(List<BaseCustomItem> data) {
        this(data, null);
    }

    public BaseCustomAdapter(List<BaseCustomItem> data, onItemClickListener listener) {
        init(data, listener);
    }

    /**
     * initialization
     * @param data
     * @param listener
     */
    public void init(List<BaseCustomItem> data, onItemClickListener listener) {
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    public BaseCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new BaseCustomViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(BaseCustomViewHolder holder, int position) {
        mLogger.i("position = " + position);
        holder.setTitle(mData.get(position).getTitle());
        holder.setDesc(mData.get(position).getDesc());
        if (mData.get(position).getIcon() == null) {
            holder.setIconById(mData.get(position).getIconId());
        } else {
            holder.setIcon(mData.get(position).getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
}
