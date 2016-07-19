package com.example.soz.recyclerView.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zhushaolong.soztest.R;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class BaseCustomViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;

    public BaseCustomViewHolder(View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
    }

    public void setTitle(String name) {
        mTitle.setText(name);
    }
}
