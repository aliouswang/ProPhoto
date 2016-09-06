package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.photo.library.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Scale SimpleDraweeView
 *
 * Created by aliouswang on 16/6/29.
 */
public class ScaleSimpleDraweeView extends SimpleDraweeView {

    private float scale = 1.0f;
    //if reverse , we set height align width
    private boolean reverse = false;

    public ScaleSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ScaleSimpleDraweeView(Context context) {
        super(context);
    }

    public ScaleSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ScaleImageView, 0, 0);
        scale = t.getFloat(R.styleable.ScaleImageView_siv_scale, 1.0f);
        reverse = t.getBoolean(R.styleable.ScaleImageView_reverse, false);
        t.recycle();
    }

    public void setScale(float scale) {
        this.scale = scale;
        requestLayout();
        invalidate();
    }

    public float getScale() {
        return this.scale;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = View.MeasureSpec.getSize(widthMeasureSpec);
        float height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (reverse) {
            width = height * scale;
        }else {
            height = width * scale;
        }

        setMeasuredDimension((int)width, (int)height);
    }
}
