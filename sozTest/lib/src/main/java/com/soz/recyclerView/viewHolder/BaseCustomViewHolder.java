package com.soz.recyclerView.viewHolder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soz.lib.R;
import com.soz.recyclerView.adapter.BaseCustomAdapter;

/**
 * Created by zhushaolong on 7/19/16.
 */
public class BaseCustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mDesc;
    private BaseCustomAdapter.onItemClickListener mListener;

    public BaseCustomViewHolder(View itemView, BaseCustomAdapter.onItemClickListener listener) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.icon) ;
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mDesc = (TextView) itemView.findViewById(R.id.desc);
        mListener = listener;
        this.initEvent(itemView);
    }

    /**
     * init listener
     * @param view
     */
    private void initEvent(View view) {
        view.setOnClickListener(this);
    }

    /**
     * fill icon data by id
     * @param id
     */
    public void setIconById(int id) {
        this.mImageView.setImageResource(id);
    }

    /**
     * fill icon data by drawable
     * @param drawable
     */
    public void setIcon(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
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

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}
