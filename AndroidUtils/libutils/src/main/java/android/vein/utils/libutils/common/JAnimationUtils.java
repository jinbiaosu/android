package android.vein.utils.libutils.common;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * Created by vein on 16/8/25.
 */
public class JAnimationUtils {
    public static void shakeView(final View v, Animation.AnimationListener animationListener) {
        int pivotX = v.getWidth() / 2;
        int pivotY = v.getHeight() / 2;
        long duration = 200;
        long startTimeMillis = 0;
        int degree = 15;
        AnimationSet shakeAnimation = new AnimationSet(v.getContext(), null);
        Animation animation = createRotateAnimation(0, -degree, pivotX, pivotY, duration / 2, startTimeMillis);
        shakeAnimation.addAnimation(animation);
        startTimeMillis = startTimeMillis + animation.getDuration();
        int offsetDegree = 0;
        for (int i = 0; i < 3; i++) {
            offsetDegree = degree * 2 * (i % 2 == 0 ? 1 : -1);
            animation = createRotateAnimation(0, offsetDegree, pivotX, pivotY, duration, startTimeMillis);
            shakeAnimation.addAnimation(animation);
            startTimeMillis = startTimeMillis + animation.getDuration();
        }
        animation = createRotateAnimation(0, -offsetDegree / 2, pivotX, pivotY, duration, startTimeMillis);
        shakeAnimation.addAnimation(animation);
        startTimeMillis = startTimeMillis + animation.getDuration();
        if (animationListener == null) {
            animationListener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.clearAnimation();
                }
            };
        }
        shakeAnimation.setAnimationListener(animationListener);
        shakeAnimation.setFillAfter(true);
        v.startAnimation(shakeAnimation);
    }

    public static void scaleAndAlpha(final View view, long duration, Animation.AnimationListener animationListener) {
        AnimationSet animationSet = new AnimationSet(view.getContext(), null);
        if (duration == 0) {
            duration = 500;
        }
        Animation animation = new ScaleAnimation(1.0f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animationSet.addAnimation(animation);
        animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(duration);
        animationSet.addAnimation(animation);
        animationSet.setFillAfter(true);
        if (animationListener == null) {
            animationListener = new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.clearAnimation();
                }
            };
        }
        animationSet.setAnimationListener(animationListener);
        view.startAnimation(animationSet);
    }

    private static Animation createRotateAnimation(int fromDegrees, int toDegrees, int pivotX, int pivotY, long duration, long startTimeMillis) {
        Animation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setFillAfter(true);
        animation.setDuration(duration);
        animation.setStartOffset(startTimeMillis);
        return animation;
    }

    public static void rotateView(View view){
        RotateAnimation animation = new RotateAnimation(0, 360 * 1000000, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1400 * 1000000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

}
