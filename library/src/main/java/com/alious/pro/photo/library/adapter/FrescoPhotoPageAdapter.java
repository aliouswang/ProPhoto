package com.alious.pro.photo.library.adapter;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.List;

import me.relex.photodraweeview.PhotoDraweeView;

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
        final PhotoDraweeView img_head = (PhotoDraweeView) rootView.findViewById(R.id.img_head);

//        ImageLoadUtil.loadScaleWithFresco(img_head, mPreviewImages.get(position).getNineImageUrl());

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(mPreviewImages.get(position).getNineImageUrl()));
        controller.setOldController(img_head.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                img_head.update(imageInfo.getWidth(), imageInfo.getHeight());
//                img_head.setRatio((float)imageInfo.getWidth()/(float)imageInfo.getHeight());
            }
        });
        img_head.setController(controller.build());
//        img_head.setPhotoUri(Uri.parse(mPreviewImages.get(position).getNineImageUrl()));
        container.addView(rootView);
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
