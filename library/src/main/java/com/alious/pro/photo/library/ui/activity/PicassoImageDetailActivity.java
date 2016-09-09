package com.alious.pro.photo.library.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.adapter.PicassoPhotoPageAdapter;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.alious.pro.photo.library.model.Point;
import com.alious.pro.photo.library.utils.ImageLoadUtil;
import com.alious.pro.photo.library.widget.RatioImageView;

import java.util.ArrayList;

/**
 * Picasso image detail activity.
 * <p/>
 * Created by aliouswang on 16/9/7.
 */
public class PicassoImageDetailActivity extends BaseImageDetailActivity<RatioImageView> {

//    protected RatioImageView mMaskImageView;

    public static Intent newIntent(Context context, View view, int index, ArrayList<NineImageUrl> imageUrls,
                                   ArrayList<Point> points,
                                   float ratio, int verticalGap, int HorizontalGap) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        Intent intent = new Intent(context, PicassoImageDetailActivity.class);
        intent.putExtra(LEFT_LOCATION, screenLocation[0]).
                putExtra(TOP_LOCATION, screenLocation[1]).
                putExtra(THUMBNAIL_WIDTH, view.getWidth()).
                putExtra(THUMBNAIL_HEIGHT, view.getHeight()).
                putExtra(CLICK_INDEX, index).
                putExtra(THUMBNAIL_IMAGE_URLS, imageUrls).
                putExtra(THUMBNAIL_IMAGE_POINTS, points).
                putExtra(THUMBNAIL_RATIO, ratio).
                putExtra(VERTICAL_GAP, verticalGap).
                putExtra(HORIZONTAL_GAP, HorizontalGap);
        return intent;
    }

    @Override
    protected PagerAdapter getPagerAdapter() {
        return new PicassoPhotoPageAdapter(mNineImageUrls);
//        return new FrescoPhotoPageAdapter(mNineImageUrls);
    }

    @Override
    protected int getInflateLayout() {
        return R.layout.activity_picasso_image_detail;
    }

    @Override
    protected void initMaskImageView() {
        mMaskImageView = (RatioImageView) findViewById(R.id.img_mask);
    }

    @Override
    protected void loadImage(RatioImageView view, String imageUrl) {
        ImageLoadUtil.loadWithPicasso(this, view, imageUrl);
    }
}
