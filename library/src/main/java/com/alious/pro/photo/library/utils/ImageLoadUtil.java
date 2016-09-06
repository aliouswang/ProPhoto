package com.alious.pro.photo.library.utils;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

public class ImageLoadUtil {

    public static void loadWithFresco(SimpleDraweeView draweeView, String imageUrl) {
        draweeView.setImageURI(Uri.parse(imageUrl + ""));
    }

}
