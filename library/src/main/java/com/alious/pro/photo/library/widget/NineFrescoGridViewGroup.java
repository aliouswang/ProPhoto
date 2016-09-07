package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.photo.library.ui.activity.BaseImageDetailActivity;
import com.alious.pro.photo.library.utils.ImageLoadUtil;

/**
 * Created by aliouswang on 16/9/6.
 */
public class NineFrescoGridViewGroup extends NineGridViewGroup<RatioSimpleDraweeView>{

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
        getContext().startActivity(BaseImageDetailActivity.newIntent(getContext(),
                view, index, mGridAdapter.getItemList(),
                ((RatioSimpleDraweeView)view).getRatio()));
    }

    @Override
    protected RatioSimpleDraweeView generateChildView(int pos) {
        return new RatioSimpleDraweeView(getContext());
    }

    @Override
    protected void loadImage(RatioSimpleDraweeView scaleSimpleDraweeView, String imageUrl) {
        ImageLoadUtil.loadScaleWithFresco(scaleSimpleDraweeView,
                imageUrl);
    }
}
