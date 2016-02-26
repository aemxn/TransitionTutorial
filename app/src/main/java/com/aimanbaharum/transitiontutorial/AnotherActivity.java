package com.aimanbaharum.transitiontutorial;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by cliqers on 26/2/2016.
 */
public class AnotherActivity extends AppCompatActivity {


    private RelativeLayout mRLContainer;
    private LinearLayout mLlContainer;
    private ImageView mIvClose;
    private FloatingActionButton mFab;

    private ImageView iv_twitter;
    private ImageView iv_github;
    private ImageView iv_linkedin;
    private ImageView iv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_activity);

        mRLContainer = (RelativeLayout) findViewById(R.id.activity_contact_rl_container);
        mLlContainer = (LinearLayout) findViewById(R.id.activity_contact_ll_container);
        mIvClose = (ImageView) findViewById(R.id.activity_contact_iv_close);
        mFab = (FloatingActionButton) findViewById(R.id.activity_contact_fab);

        iv_twitter = (ImageView) findViewById(R.id.activity_contact_iv_twitter);
        iv_github = (ImageView) findViewById(R.id.activity_contact_iv_github);
        iv_linkedin = (ImageView) findViewById(R.id.activity_contact_iv_linkedin);
        iv_email = (ImageView) findViewById(R.id.activity_contact_iv_email);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        } else {
            initViews();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater
                .from(this)
                .inflateTransition(R.transition.change_bound_with_arc);
        transition.setDuration(300);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow(mRLContainer);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow(View rl_child) {
        int cx = (rl_child.getLeft() + rl_child.getRight()) / 2;
        int cy = (rl_child.getTop() + rl_child.getBottom()) / 2;
        GUIUtils.animateRevealShow(this, rl_child, mFab.getWidth() / 2, R.color.colorAccent,
                cx, cy, new OnRevealAnimationListener() {

                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        initViews();
                    }
                });
    }

    private void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation fade = AnimationUtils.loadAnimation(AnotherActivity.this, android.R.anim.fade_in);
                fade.setDuration(300);
                Animation left = AnimationUtils.loadAnimation(AnotherActivity.this, android.R.anim.slide_in_left);
                left.setDuration(400);
                mLlContainer.startAnimation(fade);
                mIvClose.startAnimation(fade);
                iv_email.startAnimation(left);
                iv_github.startAnimation(left);
                iv_linkedin.startAnimation(left);
                iv_twitter.startAnimation(left);
                mLlContainer.setVisibility(View.VISIBLE);
                mIvClose.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        GUIUtils.animateRevealHide(this, mRLContainer, R.color.colorAccent, mFab.getWidth() / 2,
                new OnRevealAnimationListener() {

                    @Override
                    public void onRevealHide() {
                        backPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    private void backPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(getResources().getInteger(R.integer.animation_duration));
    }

}
