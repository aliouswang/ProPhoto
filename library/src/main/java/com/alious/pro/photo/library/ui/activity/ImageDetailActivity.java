package com.alious.pro.photo.library.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.alious.pro.photo.library.R;
import com.alious.pro.photo.library.adapter.FrescoPhotoPageAdapter;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.alious.pro.photo.library.widget.RatioImageView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * https://github.com/StackTipsLab/Android-GridView-Advance-Tutorial
 * <p/>
 * Created by aliouswang on 16/9/1.
 */
public class ImageDetailActivity extends Activity {

//    private ScaleSimpleDraweeView img_head;
    private RatioImageView img_head;

    private View main_background;

    private ColorDrawable mColorDrawable;

    private static final int ANIM_DURATION = 300;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private float mScale;

    private String mImageUrl;

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private int mCurrentPosition;

    private int screenHeight;
    private int screenWidth;

    private ViewPager view_pager;

    private ArrayList<NineImageUrl> mNineImageUrls;

    public static final String LEFT_LOCATION = "left_location";
    public static final String TOP_LOCATION = "right_location";
    public static final String THUMBNAIL_WIDTH = "thumbnail_width";
    public static final String THUMBNAIL_HEIGHT = "thumbnail_height";
    public static final String CLICK_INDEX = "click_index";
    public static final String THUMBNAIL_IMAGE_URLS = "thumbnail_image_urls";
    public static final String THUMBNAIL_RATIO = "thumbnail_ratio";


    public static Intent newIntent(Context context, View view, int index, ArrayList<NineImageUrl> imageUrls,
                                   float ratio) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(LEFT_LOCATION, screenLocation[0]).
                putExtra(TOP_LOCATION, screenLocation[1]).
                putExtra(THUMBNAIL_WIDTH, view.getWidth()).
                putExtra(THUMBNAIL_HEIGHT, view.getHeight()).
                putExtra(CLICK_INDEX, index).
                putExtra(THUMBNAIL_IMAGE_URLS, imageUrls).
                putExtra(THUMBNAIL_RATIO, ratio);
        return intent;
    }

    private void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        thumbnailTop = bundle.getInt(TOP_LOCATION);
        thumbnailLeft = bundle.getInt(LEFT_LOCATION);
        thumbnailWidth = bundle.getInt(THUMBNAIL_WIDTH);
        thumbnailHeight = bundle.getInt(THUMBNAIL_HEIGHT);
        mCurrentPosition = bundle.getInt(CLICK_INDEX);
        mNineImageUrls = (ArrayList<NineImageUrl>) bundle.getSerializable(THUMBNAIL_IMAGE_URLS);
        mScale = bundle.getFloat(THUMBNAIL_RATIO);
        mImageUrl = mNineImageUrls.get(mCurrentPosition).getNineImageUrl();
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
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_img_detail);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        initView();

        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = img_head.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    img_head.getViewTreeObserver().removeOnPreDrawListener(this);


//                    ImageLoadUtil.loadWithFresco(img_head, mImageUrl);
//                    Glide.with(ImageDetailActivity.this).load(mImageUrl).into(img_head);
                    Picasso.with(ImageDetailActivity.this).load(mImageUrl).into(img_head);

                    float measureWidth = img_head.getMeasuredWidth();
                    float measureHeight = img_head.getMeasuredHeight();
                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    img_head.getLocationOnScreen(screenLocation);
//                    mLeftDelta = thumbnailLeft - (int)(screenLocation[0] - (thumbnailWidth - measureWidth)/2);
//                    mTopDelta = thumbnailTop - (int)(screenLocation[1] - - (thumbnailWidth - measureWidth)/2);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    Log.e("prophoto", "left:" + screenLocation[0]
                            + ";top:" + screenLocation[1]
                            + ";mLeftDelta:" + mTopDelta
                            + ";mTopDelta:" + mTopDelta
                            + ";measureWidth:" + measureWidth
                            + ";mearuseHeight:" + measureHeight
                            + ";scale:" + mScale
                    );

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / (float) measureWidth;
                    mHeightScale = (float) thumbnailHeight / (float) measureHeight;


                    AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
                    ViewWrapper viewWrapper = new ViewWrapper(img_head);

                    ValueAnimator animator2 = ObjectAnimator.ofInt(viewWrapper, "width", thumbnailWidth);
                    ValueAnimator translateX = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
                    ValueAnimator translateY = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);
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
        main_background = findViewById(R.id.main_background);
        img_head = (RatioImageView) findViewById(R.id.img_head);
//        img_head.setScale(mScale);
        mColorDrawable = new ColorDrawable(Color.BLACK);
        main_background.setBackgroundDrawable(mColorDrawable);

        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ValueAnimator translateX = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
//                ValueAnimator translateY = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);
//                AnimatorSet translateSet = new AnimatorSet();
//                translateSet.playTogether(translateX, translateY);
//                translateSet.setDuration(0).start();
            }
        });

        FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) img_head.getLayoutParams();
        flp.width = thumbnailWidth;
        flp.height = thumbnailHeight;
        img_head.setLayoutParams(flp);
        img_head.requestLayout();

        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setVisibility(View.GONE);
        FrescoPhotoPageAdapter photoPageAdapter = new
                FrescoPhotoPageAdapter(mNineImageUrls);
        view_pager.setAdapter(photoPageAdapter);
        view_pager.setCurrentItem(mCurrentPosition);

        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
//                ImageLoadUtil.loadWithFresco(img_head, Photo.images[mCurrentPosition]);
                Glide.with(ImageDetailActivity.this)
                        .load(mNineImageUrls.get(mCurrentPosition)).into(img_head);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void enterValueAnimation() {

        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(img_head);

//        ValueAnimator scaleAnimator2 =
//                ObjectAnimator.ofFloat(viewWrapper, "simpleScale", 1, mWidthScale);

//        ValueAnimator animator2 =ObjectAnimator.ofInt(viewWrapper, "width", thumbnailWidth);
//        ValueAnimator translateX = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
//        ValueAnimator translateY = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);
//        AnimatorSet translateSet = new AnimatorSet();
//        translateSet.playTogether(translateX, translateY, animator2);
//        translateSet.setDuration(0).start();

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", screenWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "simpleScale", 1, mScale);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(img_head, "translationX", mLeftDelta, 0);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(img_head, "translationY", mTopDelta, 0);

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
                view_pager.setVisibility(View.VISIBLE);
//                img_head.setVisibility(View.GONE);
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

        public float getSimpleScale() {
            return mScale;
        }

        public void setSimpleScale(float scale) {
            this.mScale = scale;
//            ((ScaleSimpleDraweeView) mTarget).setScale(scale);
            ((RatioImageView) mTarget).setRatio(scale);
        }

    }

//    public void enterAnimation() {
//        img_head.setPivotX(0);
//        img_head.setPivotY(0);
//        img_head.setScaleX(mWidthScale);
//        img_head.setScaleY(mHeightScale);
//        img_head.setTranslationX(mLeftDelta);
//        img_head.setTranslationY(mTopDelta);
//
//        // interpolator where the rate of change starts out quickly and then decelerates.
//        TimeInterpolator sDecelerator = new DecelerateInterpolator();
//
//        // Animate scale and translation to go from thumbnail to full size
//        img_head.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
//                translationX(0).translationY(0).setInterpolator(sDecelerator);
//
//        // Fade in the black background
//        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mColorDrawable, "alpha", 0, 255);
//        bgAnim.setDuration(ANIM_DURATION);
//        bgAnim.start();
//    }

    public void exitAnimation(final Runnable endAction) {
//        TimeInterpolator sInterpolator = new AccelerateInterpolator();
//        img_head.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
//                translationX(mLeftDelta).translationY(mTopDelta)
//                .setInterpolator(sInterpolator).withEndAction(endAction);

        view_pager.setVisibility(View.GONE);
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(img_head);

        ValueAnimator animator = ObjectAnimator.ofInt(viewWrapper, "width", thumbnailWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "simpleScale", mScale, 1);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);

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
