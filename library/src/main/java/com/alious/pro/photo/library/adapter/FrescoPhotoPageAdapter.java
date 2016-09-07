package com.alious.pro.photo.library.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.alious.pro.photo.library.utils.ImageLoadUtil;
import com.alious.pro.photo.library.widget.RatioSimpleDraweeView;

import java.util.List;

/**
 * Created by aliouswang on 16/9/5.
 */
public class FrescoPhotoPageAdapter extends PagerAdapter{

    private List<NineImageUrl> mPreviewImages;

    public FrescoPhotoPageAdapter(List<NineImageUrl> previewImages) {
        this.mPreviewImages = previewImages;
    }

    @Override
    public int getCount() {
        return this.mPreviewImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View rootView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.view_pager_preview, null);
        final RatioSimpleDraweeView img_head = (RatioSimpleDraweeView) rootView.findViewById(R.id.img_head);
        container.addView(rootView);
        ImageLoadUtil.loadScaleWithFresco(img_head, mPreviewImages.get(position).getNineImageUrl());
        return rootView;
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
