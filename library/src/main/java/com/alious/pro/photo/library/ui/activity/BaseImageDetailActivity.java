package com.alious.pro.photo.library.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.adapter.FrescoPhotoPageAdapter;
import com.alious.pro.photo.library.interfaces.IRatio;
import com.alious.pro.photo.library.interfaces.NineImageUrl;

import java.util.ArrayList;


/**
 * https://github.com/StackTipsLab/Android-GridView-Advance-Tutorial
 * <p/>
 * Created by aliouswang on 16/9/1.
 */
public abstract class BaseImageDetailActivity<T extends View> extends Activity {

    public static final int ANIM_DURATION = 300;

    public static final String LEFT_LOCATION = "left_location";
    public static final String TOP_LOCATION = "right_location";
    public static final String THUMBNAIL_WIDTH = "thumbnail_width";
    public static final String THUMBNAIL_HEIGHT = "thumbnail_height";
    public static final String CLICK_INDEX = "click_index";
    public static final String THUMBNAIL_IMAGE_URLS = "thumbnail_image_urls";
    public static final String THUMBNAIL_RATIO = "thumbnail_ratio";
    public static final String HORIZONTAL_GAP = "horizontal_gap";
    public static final String VERTICAL_GAP = "vertical_gap";

    protected T mMaskImageView;

    private View mMainBackground;
    private ColorDrawable mColorDrawable;

    private int mLeftDelta;
    private int mTopDelta;
    private float mRatio;
    private int mHorizontalGap;
    private int mVerticalGap;

    private int mThumbnailTop;
    private int mThumbnailLeft;
    private int mThumbnailWidth;
    private int mThumbnailHeight;

    private String mCurrentImageUrl;
    private int mCurrentPosition;

    private int mScreenHeight;
    private int mScreenWidth;

    private ViewPager mViewPager;

    private ArrayList<NineImageUrl> mNineImageUrls;

    private void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        mThumbnailTop = bundle.getInt(TOP_LOCATION);
        mThumbnailLeft = bundle.getInt(LEFT_LOCATION);
        mThumbnailWidth = bundle.getInt(THUMBNAIL_WIDTH);
        mThumbnailHeight = bundle.getInt(THUMBNAIL_HEIGHT);
        mCurrentPosition = bundle.getInt(CLICK_INDEX);
        mNineImageUrls = (ArrayList<NineImageUrl>) bundle.getSerializable(THUMBNAIL_IMAGE_URLS);
        mRatio = bundle.getFloat(THUMBNAIL_RATIO);
        mHorizontalGap = bundle.getInt(HORIZONTAL_GAP);
        mVerticalGap = bundle.getInt(VERTICAL_GAP);
        mCurrentImageUrl = mNineImageUrls.get(mCurrentPosition).getNineImageUrl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(getInflateLayout());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        mScreenWidth = displaymetrics.widthPixels;

        initView();

        if (savedInstanceState == null) {
            ViewTreeObserver observer = mMaskImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mMaskImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    loadImage(mMaskImageView, mCurrentImageUrl);

                    int[] screenLocation = new int[2];
                    mMaskImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = mThumbnailLeft - screenLocation[0];
                    mTopDelta = mThumbnailTop - screenLocation[1];

                    AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
                    ViewWrapper viewWrapper = new ViewWrapper(mMaskImageView);

                    ValueAnimator animator2 = ObjectAnimator.ofInt(viewWrapper, "width", mThumbnailWidth);
                    ValueAnimator translateX = ObjectAnimator.ofFloat(mMaskImageView, "translationX", 0, mLeftDelta);
                    ValueAnimator translateY = ObjectAnimator.ofFloat(mMaskImageView, "translationY", 0, mTopDelta);
                    AnimatorSet translateSet = new AnimatorSet();
                    translateSet.playTogether(translateX, translateY, animator2);
                    translateSet.setDuration(0).start();

                    enterValueAnimation();
                    return true;
                }
            });

        }
    }


    private void initView() {
        mMainBackground = findViewById(R.id.main_background);
        mColorDrawable = new ColorDrawable(Color.BLACK);
        mMainBackground.setBackgroundDrawable(mColorDrawable);

        initMaskImageView();

        FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) mMaskImageView.getLayoutParams();
        flp.width = mThumbnailWidth;
        flp.height = mThumbnailHeight;
        mMaskImageView.setLayoutParams(flp);
        mMaskImageView.requestLayout();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setVisibility(View.GONE);
        FrescoPhotoPageAdapter photoPageAdapter = new
                FrescoPhotoPageAdapter(mNineImageUrls);
        mViewPager.setAdapter(photoPageAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                loadImage(mMaskImageView, mNineImageUrls.get(mCurrentPosition).getNineImageUrl());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected abstract int getInflateLayout();
    protected abstract void initMaskImageView();
    protected abstract void loadImage(T view, String imageUrl);

    public void enterValueAnimation() {

        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(mMaskImageView);

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", mScreenWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "ratio", 1, mRatio);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationX", mLeftDelta, 0);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationY", mTopDelta, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnimator, translateXAnim, translateYAnim);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setStartDelay(10);
        animatorSet.setDuration(ANIM_DURATION).start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mViewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private static class ViewWrapper {
        private View mTarget;

        private float mScale = 1;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        public float getRatio() {
            return mScale;
        }

        public void setRatio(float scale) {
            this.mScale = scale;
            ((IRatio) mTarget).setRatio(scale);
        }

    }

    public void exitAnimation(final Runnable endAction) {
        mViewPager.setVisibility(View.GONE);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(mMaskImageView);

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", mThumbnailWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "simpleScale", mRatio, 1);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationX", 0, mLeftDelta);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(mMaskImageView, "translationY", 0, mTopDelta);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnimator, translateXAnim, translateYAnim);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setStartDelay(10);
        animatorSet.setDuration(ANIM_DURATION).start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                endAction.run();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mColorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}
