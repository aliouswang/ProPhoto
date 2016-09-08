package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.alious.pro.photo.library.ui.activity.PicassoImageDetailActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by aliouswang on 16/9/7.
 */
public class NinePicassoGridViewGroup extends NineGridViewGroup<RatioImageView>{

    public NinePicassoGridViewGroup(Context context) {
        super(context);
    }

    public NinePicassoGridViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NinePicassoGridViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onItemClicked(View view, int index) {
        getContext().startActivity(PicassoImageDetailActivity.newIntent(
                getContext(),
                view,
                index,
                mGridAdapter.getItemList(),
                mPoints,
                ((RatioImageView)view).getRatio(),
                mVerticalGap,
                mHorizontalGap));
    }

    @Override
    protected RatioImageView generateChildView(int pos) {
        RatioImageView ratioImageView = new RatioImageView(getContext());
        ratioImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return ratioImageView;
    }

    @Override
    protected void loadImage(RatioImageView imageView, String imageUrl) {
//        Glide.with(getContext()).load(imageUrl).into(imageView);
        Picasso.with(getContext()).load(imageUrl).into(imageView);
    }
}
