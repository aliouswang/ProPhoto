package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

/**
 * Created by aliouswang on 16/9/7.
 */
public class RatioImageView extends ImageView{

    private float ratio = 1.0f;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable instanceof BitmapDrawable) {
            this.ratio = (float)drawable.getIntrinsicWidth() / (float)drawable.getIntrinsicHeight();
            Log.e("prophoto", this.ratio + "");
        }else if (drawable instanceof GlideBitmapDrawable) {
            this.ratio = (float)drawable.getIntrinsicWidth()/ (float)drawable.getIntrinsicHeight();
            Log.e("prophoto", this.ratio + "");
        }
    }
}
