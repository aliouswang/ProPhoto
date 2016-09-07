package com.alious.pro.photo.library.ui.activity;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.utils.ImageLoadUtil;
import com.alious.pro.photo.library.widget.RatioSimpleDraweeView;

/**
 * Fresco image detail activity
 *
 * Created by aliouswang on 16/9/7.
 */
public class FrescoImageDetailActivity
        extends BaseImageDetailActivity<RatioSimpleDraweeView>{

    protected RatioSimpleDraweeView mMaskImageView;

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_img_detail;
    }

    @Override
    protected void initMaskImageView() {
        mMaskImageView = (RatioSimpleDraweeView) findViewById(R.id.img_head);
    }

    @Override
    protected void loadImage(RatioSimpleDraweeView view, String imageUrl) {
        ImageLoadUtil.loadScaleWithFresco(view, imageUrl);
    }
}
