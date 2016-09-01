package com.alious.pro.simple;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;


/**
 * https://github.com/StackTipsLab/Android-GridView-Advance-Tutorial
 *
 * Created by aliouswang on 16/9/1.
 */
public class ImageDetailActivity extends Activity{

    private ImageView img_head;
    private View main_background;

    private ColorDrawable mColorDrawable;


    private static final int ANIM_DURATION = 600;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
        setContentView(R.layout.activity_img_detail);
        initView();

        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (savedInstanceState == null) {
            ViewTreeObserver observer = img_head.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    img_head.getViewTreeObserver().removeOnPreDrawListener(this);

                    // Figure out where the thumbnail and full size versions are, relative
                    // to the screen and each other
                    int[] screenLocation = new int[2];
                    img_head.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    int measureWidth = img_head.getMeasuredWidth();
                    int measureHeight = img_head.getMeasuredHeight();

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / measureWidth;
                    mHeightScale = (float) thumbnailHeight / measureHeight;

                    enterAnimation();

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
    }

    private void initView() {
        main_background = findViewById(R.id.main_background);
        img_head = (ImageView) findViewById(R.id.img_head);

        mColorDrawable = new ColorDrawable(Color.BLACK);
        main_background.setBackgroundDrawable(mColorDrawable);


    }

    public void enterAnimation() {
        img_head.setPivotX(0);
        img_head.setPivotY(0);
        img_head.setScaleX(mWidthScale);
        img_head.setScaleY(mHeightScale);
        img_head.setTranslationX(mLeftDelta);
        img_head.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        img_head.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(mColorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    public void exitAnimation(final Runnable endAction) {
        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        img_head.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

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
            }
        });
    }
}
