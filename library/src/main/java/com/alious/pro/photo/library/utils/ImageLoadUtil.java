package com.alious.pro.photo.library.utils;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.alious.pro.photo.library.widget.ScaleSimpleDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

public class ImageLoadUtil {

    public static void loadWithFresco(SimpleDraweeView draweeView, String imageUrl) {
        draweeView.setImageURI(Uri.parse(imageUrl + ""));
    }

    public static void loadWithFresco(final ScaleSimpleDraweeView scaleDraweeView, String imageUrl) {
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
                scaleDraweeView.setScale(mScale);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
            }
        };
        Uri uri = Uri.parse(imageUrl);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(uri)
                .build();
        scaleDraweeView.setController(controller);
    }

}
