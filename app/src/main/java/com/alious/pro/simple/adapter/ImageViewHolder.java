package com.alious.pro.simple.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alious.pro.photo.library.widget.NineFrescoGridViewGroup;
import com.alious.pro.simple.R;

/**
 * Created by aliouswang on 16/9/8.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder{

    public NineFrescoGridViewGroup mNineFrescoGridViewGroup;

    public ImageViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        mNineFrescoGridViewGroup = (NineFrescoGridViewGroup) itemView.findViewById(R.id.nine_fresco_gridview);
    }
}
