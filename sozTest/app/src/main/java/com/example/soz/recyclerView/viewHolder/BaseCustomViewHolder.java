package com.example.soz.recyclerView.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhushaolong.soztest.R;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class BaseCustomViewHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mDesc;

    public BaseCustomViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.icon) ;
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mDesc = (TextView) itemView.findViewById(R.id.desc);
    }

    /**
     * fill icon data
     * @param id
     */
    public void setIconById(int id) {
        this.mImageView.setImageResource(id);
    }

    /**
     * fill title data
     * @param name
     */
    public void setTitle(String name) {
        mTitle.setText(name);
    }

    /**
     * fill description data
     * @param desc
     */
    public void setDesc(String desc) {
        this.mDesc.setText(desc);
    }
}
