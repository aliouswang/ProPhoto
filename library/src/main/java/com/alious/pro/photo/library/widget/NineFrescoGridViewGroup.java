package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.photo.library.ui.activity.FrescoImageDetailActivity;
import com.alious.pro.photo.library.utils.ImageLoadUtil;

/**
 * Created by aliouswang on 16/9/6.
 */
public class NineFrescoGridViewGroup extends NineGridViewGroup<RatioSimpleDraweeView>{

    private Context mContext;

    public NineFrescoGridViewGroup(Context context) {
        super(context);
        mContext = context;
    }

    public NineFrescoGridViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public NineFrescoGridViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onItemClicked(View view, int index) {
        getContext().startActivity(FrescoImageDetailActivity.newIntent(
                getContext(),
                view,
                index,
                mGridAdapter.getItemList(),
                mPoints,
                ((RatioSimpleDraweeView)view).getRatio(),
                mVerticalGap,
                mHorizontalGap)
                );
    }

    @Override
    protected RatioSimpleDraweeView generateChildView(int pos) {
        return new RatioSimpleDraweeView(getContext());
    }

    @Override
    protected void loadImage(final RatioSimpleDraweeView scaleSimpleDraweeView, String imageUrl) {
        ImageLoadUtil.loadScaleWithFresco(mContext, scaleSimpleDraweeView,
                imageUrl);

    }
}
