package com.alious.pro.simple;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/9/5.
 */
public class FrescoPhotoPageAdapter extends PagerAdapter{

    private ArrayList<String> mPreviewImages;

    public FrescoPhotoPageAdapter(ArrayList<String> previewImages) {
        this.mPreviewImages = previewImages;
    }

    @Override
    public int getCount() {
        return this.mPreviewImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ScaleSimpleDraweeView imageView = new ScaleSimpleDraweeView(container.getContext());
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageLoadUtil.loadWithFresco(imageView, mPreviewImages.get(position));
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
