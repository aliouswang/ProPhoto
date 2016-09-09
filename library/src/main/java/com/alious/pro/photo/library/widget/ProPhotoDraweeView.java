package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by aliouswang on 16/9/9.
 */
public class ProPhotoDraweeView extends PhotoDraweeView{


    public ProPhotoDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ProPhotoDraweeView(Context context) {
        super(context);
    }

    public ProPhotoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//
}
