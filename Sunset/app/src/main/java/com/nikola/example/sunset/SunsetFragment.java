package com.nikola.example.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class SunsetFragment extends Fragment{

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean mIsSunDown;
    private boolean mIsSunUp;
    private int mSunInitialY;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mIsSunUp = true;
        mIsSunDown = false;

        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsSunUp) {
                    startSunsetAnimation();
                }
                if (mIsSunDown) {
                    startSunriseAnimation();
                }
            }
        });

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSunInitialY = mSunView.getTop();
    }

    private void startSunriseAnimation() {
        float sunYStart = mSkyView.getHeight();
        float sunYEnd = mSunView.getTop();

        ObjectAnimator heightAnimator =
                ObjectAnimator
                        .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                        .setDuration(3000);

        heightAnimator.setInterpolator(new AccelerateInterpolator());

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.2f);

        ObjectAnimator scaleAnimator =
                ObjectAnimator
                        .ofPropertyValuesHolder(mSunView, pvhScaleX, pvhScaleY)
                        .setDuration(500);
        scaleAnimator.setRepeatCount(3000 / 500);

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(nightSkyAnimator)
                .with(scaleAnimator)
                .before(sunsetSkyAnimator);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsSunDown = false;
                mIsSunUp = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsSunUp = true;
                mIsSunDown = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void startSunsetAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        ObjectAnimator heightAnimator =
                ObjectAnimator
                        .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                        .setDuration(3000);

        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.2f);

        ObjectAnimator scaleAnimator =
                ObjectAnimator
                        .ofPropertyValuesHolder(mSunView, pvhScaleX, pvhScaleY)
                        .setDuration(500);
        scaleAnimator.setRepeatCount(3000 / 500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(scaleAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsSunUp = false;
                mIsSunDown = false;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsSunDown = true;
                mIsSunUp = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
