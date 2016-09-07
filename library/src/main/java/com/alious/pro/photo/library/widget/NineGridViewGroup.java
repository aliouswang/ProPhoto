package com.alious.pro.photo.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.interfaces.NineGridDataSource;
import com.alious.pro.photo.library.interfaces.NineGridDelegate;

import java.util.ArrayList;

/**
 * Nine grid view group,
 *
 * Created by aliouswang on 16/9/5.
 */
public abstract class NineGridViewGroup<T extends View> extends ViewGroup{

    public static final int DEFAULT_MAX_SIZE = 9;
    public static final int DEFAULT_COLUMN_SIZE = 3;
    public static final float DEFAULT_RATIO = 1.0f;

    private int mMaxSize;
    private int mRowCount;
    private int mColumnCount;
    private int mHorizontalGap;
    private int mVerticalGap;
    private float mRatio;

    private float mCellWidth;
    private float mCellHeight;

    private ArrayList<Point> mPoints;

    protected NineGridDataSource mGridAdapter;
    private NineGridDelegate mGridDelegate;

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
        this.mColumnCount = typedArray.getInt(R.styleable.NineGridViewGroup_columnSize,
                DEFAULT_COLUMN_SIZE);
        this.mHorizontalGap = typedArray.getDimensionPixelSize(R.styleable.NineGridViewGroup_horizontal_gap,
                getResources().getDimensionPixelSize(R.dimen.default_horizontal_gap));
        this.mVerticalGap = typedArray.getDimensionPixelSize(R.styleable.NineGridViewGroup_vertical_gap,
                getResources().getDimensionPixelSize(R.dimen.default_vertical_gap));
        this.mRatio = typedArray.getFloat(R.styleable.NineGridViewGroup_ratio,
                DEFAULT_RATIO);
        typedArray.recycle();
    }

    public void setGridAdapter(NineGridDataSource gridAdapter) {
        mGridAdapter = gridAdapter;
        calculateCellPoint();
        reuseChildrenView();
        requestLayout();
    }

    public void setGridDelegate(NineGridDelegate gridDelegate) {
        this.mGridDelegate = gridDelegate;
    }

    private void reuseChildrenView() {
        int currentChildrenCount = getChildCount();
        int count = getCount();
        if (currentChildrenCount > count) {
            removeViews(count, currentChildrenCount - count);
        }else if (currentChildrenCount < count){
            for (int i = 0; i < count - currentChildrenCount; i ++) {
                addView(generateChildView(i + currentChildrenCount));
            }
        }
        for (int i = 0; i < count; i ++) {
            loadImage((T) getChildAt(i), getUrlByPosition(i));
            final int index = i;
            getChildAt(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
//                    mGridDelegate.onItemClicked(view, mGridAdapter.getItem(index), index);
                    onItemClicked(view, index);
                }
            });
        }
    }

    protected abstract void onItemClicked(View view, int index);

    private void calculateCellPoint() {
        int count = getCount();
        if (count > 0) {
            mPoints = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                Point point = new Point();
                point.row = i % mColumnCount;
                point.column = i / mColumnCount;
                mPoints.add(point);
            }
            mRowCount = (int) Math.ceil(count / mColumnCount);
        }else {
            mPoints = new ArrayList<>();
        }
    }

    private Point getPointByPosition(int pos) {
        return mPoints.get(pos);
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

    public int getCount() {
        return mGridAdapter != null ?
                Math.min(mGridAdapter.getCount(), mMaxSize) : 0;
    }

    public String getUrlByPosition(int pos) {
        return mGridAdapter != null ? mGridAdapter.getItem(pos).getNineImageUrl() : "";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mGridAdapter.getCount() <= 0) {
            setMeasuredDimension(sWidth, sHeight);
        }else {
            int totalSpace = sWidth - getPaddingLeft() - getPaddingRight();
            if (isSingle()) {
                mCellWidth = totalSpace;
            }else {
                mCellWidth = (totalSpace - mHorizontalGap * (mColumnCount - 1)) / mColumnCount;
            }
            mCellHeight = mCellWidth * mRatio;
            float totalHeight = mCellHeight * mRowCount + getPaddingTop() + getPaddingBottom()
                    + (mRowCount - 1) * mVerticalGap;
            setMeasuredDimension(sWidth, (int)totalHeight);
        }
    }

    @Override
    protected void onLayout(boolean b, int l, int i1, int i2, int i3) {
        int count = getCount();
        if (count <= 0 || getChildCount() < count) return;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            float left = getPointByPosition(i).column * (mCellWidth + mHorizontalGap) + getPaddingLeft();
            float top = getPointByPosition(i).row * (mCellHeight + mVerticalGap) + getPaddingTop();
            childView.layout((int)left, (int)top, (int)(left + mCellWidth), (int)(top + mCellHeight));
        }
    }

    protected abstract T generateChildView(int pos);

    protected abstract void loadImage(T t, String imageUrl);

    private class Point {
        int row;
        int column;
    }
}
