package com.alious.pro.photo.library.ui.activity;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.widget.RatioImageView;

/**
 * Picasso image detail activity.
 *
 * Created by aliouswang on 16/9/7.
 */
public class PicassoImageDetailActivity extends BaseImageDetailActivity<RatioImageView>{

    protected RatioImageView mMaskImageView;

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_img_detail;
    }

    @Override
    protected void initMaskImageView() {
        mMaskImageView = (RatioImageView) findViewById(R.id.img_head);
    }

    @Override
    protected void loadImage(RatioImageView view, String imageUrl) {

    }
}
