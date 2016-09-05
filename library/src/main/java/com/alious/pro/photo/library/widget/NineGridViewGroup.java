package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.INineGridAdapter;

/**
 * Nine grid view group,
 *
 * Created by aliouswang on 16/9/5.
 */
public class NineGridViewGroup extends ViewGroup{

    public static final int DEFAULT_MAX_SIZE = 9;
    public static final float DEFAULT_RATIO = 1.0f;

    private int mMaxSize;
    private int mHorizontalGap;
    private int mVerticalGap;
    private float mRatio;

    private INineGridAdapter mGridAdapter;

    public NineGridViewGroup(Context context) {
        this(context, null);
    }

    public NineGridViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.NineGridViewGroup);
        this.mMaxSize = typedArray.getInt(R.styleable.NineGridViewGroup_maxSize,
                DEFAULT_MAX_SIZE);
        this.mHorizontalGap = typedArray.getDimensionPixelSize(R.styleable.NineGridViewGroup_horizontal_gap,
                getResources().getDimensionPixelSize(R.dimen.default_horizontal_gap));
        this.mVerticalGap = typedArray.getDimensionPixelSize(R.styleable.NineGridViewGroup_vertical_gap,
                getResources().getDimensionPixelSize(R.dimen.default_vertical_gap));
        this.mRatio = typedArray.getFloat(R.styleable.NineGridViewGroup_ratio,
                DEFAULT_RATIO);
        typedArray.recycle();
    }

    public void setGridAdapter(INineGridAdapter gridAdapter) {
        mGridAdapter = gridAdapter;
        requestLayout();
    }

    /**
     * If is only one image
     *
     * @return
     */
    public boolean isSingle() {
        if (mGridAdapter != null) {
            return mGridAdapter.getCount() == 1;
        }else return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mGridAdapter.getCount() <= 0) {
            setMeasuredDimension(sWidth, sHeight);
        }else {

        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
