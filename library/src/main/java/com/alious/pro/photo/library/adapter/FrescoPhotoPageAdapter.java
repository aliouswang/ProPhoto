package com.alious.pro.photo.library.adapter;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.widget.ScaleSimpleDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.List;

/**
 * Created by aliouswang on 16/9/5.
 */
public class FrescoPhotoPageAdapter extends PagerAdapter{

    private List<String> mPreviewImages;

    public FrescoPhotoPageAdapter(List<String> previewImages) {
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
        final ScaleSimpleDraweeView img_head = (ScaleSimpleDraweeView) rootView.findViewById(R.id.img_head);
        container.addView(rootView);
//        ImageLoadUtil.loadScaleWithFresco(imageView, mPreviewImages.get(position));
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                float mScale = (float) imageInfo.getHeight() / (float) imageInfo.getWidth();
                img_head.setScale(mScale);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        Uri uri = Uri.parse(mPreviewImages.get(position));
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(uri)
                .build();
        img_head.setController(controller);

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
