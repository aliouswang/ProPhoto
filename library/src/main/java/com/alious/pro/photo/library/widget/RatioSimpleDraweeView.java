package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.IRatio;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Scale SimpleDraweeView
 *
 * Created by aliouswang on 16/6/29.
 */
public class RatioSimpleDraweeView extends SimpleDraweeView implements IRatio{

    private float ratio = 1.0f;
    //if reverse , we set height align width
    private boolean reverse = false;

    public RatioSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public RatioSimpleDraweeView(Context context) {
        super(context);
    }

    public RatioSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ScaleImageView, 0, 0);
        ratio = t.getFloat(R.styleable.ScaleImageView_siv_scale, 1.0f);
        reverse = t.getBoolean(R.styleable.ScaleImageView_reverse, false);
        t.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float width = View.MeasureSpec.getSize(widthMeasureSpec);
        float height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (reverse) {
            width = height * ratio;
        }else {
            height = width * ratio;
        }

        setMeasuredDimension((int)width, (int)height);
    }

    @Override
    public float getRatio() {
        return this.ratio;
    }

    @Override
    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
        invalidate();
    }
}
