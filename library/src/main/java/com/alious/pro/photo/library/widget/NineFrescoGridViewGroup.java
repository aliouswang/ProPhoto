package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.photo.library.ui.activity.ImageDetailActivity;
import com.alious.pro.photo.library.utils.ImageLoadUtil;

/**
 * Created by aliouswang on 16/9/6.
 */
public class NineFrescoGridViewGroup extends NineGridViewGroup<ScaleSimpleDraweeView>{

    public NineFrescoGridViewGroup(Context context) {
        super(context);
    }

    public NineFrescoGridViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NineFrescoGridViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onItemClicked(View view, int index) {
        getContext().startActivity(ImageDetailActivity.newIntent(getContext(),
                view, index, mGridAdapter.getItemList(),
                ((ScaleSimpleDraweeView)view).getScale()));
    }

    @Override
    protected ScaleSimpleDraweeView generateChildView(int pos) {
        return new ScaleSimpleDraweeView(getContext());
    }

    @Override
    protected void loadImage(ScaleSimpleDraweeView scaleSimpleDraweeView, String imageUrl) {
        ImageLoadUtil.loadScaleWithFresco(scaleSimpleDraweeView,
                imageUrl);
    }
}
