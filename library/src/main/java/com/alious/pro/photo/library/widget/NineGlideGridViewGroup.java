package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.alious.pro.photo.library.ui.activity.BaseImageDetailActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by aliouswang on 16/9/7.
 */
public class NineGlideGridViewGroup extends NineGridViewGroup<RatioImageView>{

    public NineGlideGridViewGroup(Context context) {
        super(context);
    }

    public NineGlideGridViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NineGlideGridViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onItemClicked(View view, int index) {
        getContext().startActivity(BaseImageDetailActivity.newIntent(getContext(),
                view, index, mGridAdapter.getItemList(),
                ((RatioImageView)view).getRatio()));
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
