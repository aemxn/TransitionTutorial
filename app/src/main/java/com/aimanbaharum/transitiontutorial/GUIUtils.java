package com.aimanbaharum.transitiontutorial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

/**
 * Created by cliqers on 26/2/2016.
 */
public class GUIUtils {

    public static void animateRevealShow(final Context ctx, final View view,
                                         final int startRadius, final int color, int x, int y,
                                         final OnRevealAnimationListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
            anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
            anim.setStartDelay(100);
            anim.setInterpolator(new FastOutLinearInInterpolator());
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    view.setBackgroundColor(ctx.getResources().getColor(color));
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                    if (listener != null) {
                        listener.onRevealShow();
                    }
                }
            });
            anim.start();
        }
    }


    public static void animateRevealHide(final Context ctx, final RelativeLayout view,
                                         final int color, int finalRadius,
                                         final OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = view.getWidth();

        Animator anim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    view.setBackgroundColor(ctx.getResources().getColor(color));
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    listener.onRevealHide();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.setDuration(ctx.getResources().getInteger(R.integer.animation_duration));
            anim.start();
        }
    }
}
