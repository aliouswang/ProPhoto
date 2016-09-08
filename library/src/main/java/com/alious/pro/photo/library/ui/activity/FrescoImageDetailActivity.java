package com.alious.pro.photo.library.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.alious.pro.photo.library.utils.ImageLoadUtil;
import com.alious.pro.photo.library.widget.RatioSimpleDraweeView;

import java.util.ArrayList;

/**
 * Fresco image detail activity
 *
 * Created by aliouswang on 16/9/7.
 */
public class FrescoImageDetailActivity
        extends BaseImageDetailActivity<RatioSimpleDraweeView>{

//    protected RatioSimpleDraweeView mMaskImageView;

    public static Intent newIntent(Context context, View view, int index, ArrayList<NineImageUrl> imageUrls,
                                   float ratio, int verticalGap, int horizontalGap) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        Intent intent = new Intent(context, FrescoImageDetailActivity.class);
        intent.putExtra(LEFT_LOCATION, screenLocation[0]).
                putExtra(TOP_LOCATION, screenLocation[1]).
                putExtra(THUMBNAIL_WIDTH, view.getWidth()).
                putExtra(THUMBNAIL_HEIGHT, view.getHeight()).
                putExtra(CLICK_INDEX, index).
                putExtra(THUMBNAIL_IMAGE_URLS, imageUrls).
                putExtra(THUMBNAIL_RATIO, ratio).
                putExtra(VERTICAL_GAP, verticalGap).
                putExtra(HORIZONTAL_GAP, horizontalGap)
        ;
        return intent;
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_fresco_image_detail;
    }

    @Override
    protected void initMaskImageView() {
        mMaskImageView = (RatioSimpleDraweeView) findViewById(R.id.img_mask);
    }

    @Override
    protected void loadImage(RatioSimpleDraweeView view, String imageUrl) {
        ImageLoadUtil.loadScaleWithFresco(view, imageUrl);
    }
}
