package com.alious.pro.simple;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;


/**
 * https://github.com/StackTipsLab/Android-GridView-Advance-Tutorial
 *
 * Created by aliouswang on 16/9/1.
 */
public class ImageDetailActivity extends Activity{

    private ScaleSimpleDraweeView img_head;
    private View main_background;

    private ColorDrawable mColorDrawable;


    private static final int ANIM_DURATION = 300;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;
    private float mScale;

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private int screenHeight;
    private int screenWidth;

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


                    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(
                                String id,
                                @Nullable ImageInfo imageInfo,
                                @Nullable Animatable anim) {
                            if (imageInfo == null) {
                                return;
                            }
//                            img_head.setScale((float) imageInfo.getHeight() / (float) imageInfo.getWidth());
                        }

                        @Override
                        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                        }
                    };
                    Uri uri = Uri.parse(Photo.images[2]);
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setControllerListener(controllerListener)
                            .setUri(uri)
                            .build();
                    img_head.setController(controller);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    img_head.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    float measureWidth = img_head.getMeasuredWidth();
                    float measureHeight = img_head.getMeasuredHeight();

                    Log.e("prophoto", "left:" + screenLocation[0]
                            + ";top:" + screenLocation[1]
                            + ";mLeftDelta:" + mTopDelta
                            + ";mTopDelta:" + mTopDelta
                            + ";measureWidth:" + measureWidth
                            + ";mearuseHeight:" + measureHeight
                            + ";scale:" + mScale
                    );


                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / measureWidth;
                    mHeightScale = (float) thumbnailHeight / measureHeight;

                    enterValueAnimation();

                    return true;
                }
            });

        }
    }

    private void parseIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        thumbnailTop = bundle.getInt("top");
        thumbnailLeft = bundle.getInt("left");
        thumbnailWidth = bundle.getInt("width");
        thumbnailHeight = bundle.getInt("height");
        mScale = bundle.getFloat("scale");
    }

    private void initView() {
        main_background = findViewById(R.id.main_background);
        img_head = (ScaleSimpleDraweeView) findViewById(R.id.img_head);
//        img_head.setScale(mScale);
        mColorDrawable = new ColorDrawable(Color.BLACK);
        main_background.setBackgroundDrawable(mColorDrawable);

        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueAnimator translateX = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
                ValueAnimator translateY = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);
                AnimatorSet translateSet = new AnimatorSet();
                translateSet.playTogether(translateX, translateY);
                translateSet.setDuration(0).start();
            }
        });
    }

    public void enterValueAnimation() {
        LinearInterpolator interpolator = new LinearInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(img_head);

        ValueAnimator translateX = ObjectAnimator.ofFloat(img_head, "translationX", 0, mLeftDelta);
        ValueAnimator translateY = ObjectAnimator.ofFloat(img_head, "translationY", 0, mTopDelta);
        AnimatorSet translateSet = new AnimatorSet();
        translateSet.playTogether(translateX, translateY);
        translateSet.setDuration(0).start();

        ValueAnimator animator =ObjectAnimator.ofInt(viewWrapper, "width", screenWidth);
        ValueAnimator scaleAnimator =
                ObjectAnimator.ofFloat(viewWrapper, "simpleScale", 1, mScale);
        ValueAnimator translateXAnim = ObjectAnimator.ofFloat(img_head, "translationX", mLeftDelta, 0);
        ValueAnimator translateYAnim = ObjectAnimator.ofFloat(img_head, "translationY", mTopDelta, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, scaleAnimator, translateXAnim, translateYAnim);
        animatorSet.setInterpolator(interpolator);
        animatorSet.setStartDelay(10);
        animatorSet.setDuration(ANIM_DURATION).start();
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
            ((ScaleSimpleDraweeView) mTarget).setScale(scale);
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

        LinearInterpolator interpolator = new LinearInterpolator();
        ViewWrapper viewWrapper = new ViewWrapper(img_head);

        ValueAnimator animator =ObjectAnimator.ofInt(viewWrapper, "width", thumbnailWidth);
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
