package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

import com.projects.demo.util.UnitUtil;
import com.projects.demo.view.Rotate3dAnimation;

public class FrameLayoutTwoView extends Activity {
	
	private static final String tag = "FrameLayoutTwoView";
	
	AbsoluteLayout frameLayout;
	boolean textView1Visible = true;
	int currentIndex = 0;
	
	TextView textView1;
	TextView textView2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		frameLayout = new AbsoluteLayout(this);
        
        textView2 = new TextView(this);
        textView2.setBackgroundColor(Color.BLUE);
        textView2.setText("detail");
        textView2.setVisibility(View.GONE);
        textView2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				applyRotation(0, -90);
			}
		});
        
        textView1 = new TextView(this);
        textView1.setBackgroundColor(Color.RED);
        textView1.setText("mobile");
        textView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				applyRotation(0, -90);
			}
		});
        
        frameLayout.addView(textView1, new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0));
        frameLayout.addView(textView2, new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0));
        
       /* frameLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d(tag, "onTouch");
				if(textView1Visible) {
					textView2.setVisibility(View.VISIBLE);
					translate(textView2);
					textView1Visible = false;
				} else {
					translate2(textView2);
					textView2.setVisibility(View.GONE);
					textView1Visible = true;
				}
				return false;
			}
		});*/
        
        setContentView(frameLayout, new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, UnitUtil.dip2px(this, 400), 50, 50));
        
        frameLayout.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
	}
	
	/**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
    public void applyRotation(float start, float end) {
        // Find the center of the container
        final float centerX = frameLayout.getWidth() / 2.0f;
        final float centerY = frameLayout.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 0.0f, false);
        rotation.setDuration(3000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView());

        frameLayout.startAnimation(rotation);
    }
    
    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
    private final class DisplayNextView implements Animation.AnimationListener {

        private DisplayNextView() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
        	frameLayout.post(new SwapViews());
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }
    
    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
    private final class SwapViews implements Runnable {

        public SwapViews() {
        }

        public void run() {
            final float centerX = frameLayout.getWidth() / 2.0f;
            final float centerY = frameLayout.getHeight() / 2.0f;
            Rotate3dAnimation rotation;
            
            if (currentIndex == 0) {
            	textView1.setVisibility(View.GONE);
            	textView2.setVisibility(View.VISIBLE);
            	textView2.requestFocus();
                //rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            	rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            	currentIndex = 1;
            } else {
            	textView2.setVisibility(View.GONE);
            	textView1.setVisibility(View.VISIBLE);
            	textView1.requestFocus();
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            	currentIndex = 0;
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            frameLayout.startAnimation(rotation);
        }
    }
	
	private void translate(View view) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				1f, Animation.RELATIVE_TO_PARENT, 0f);
		animation.setDuration(500);
		animation.setFillAfter(true);
		animation.setInterpolator(new AccelerateInterpolator(0.3f));

		view.startAnimation(animation);
	}
	
	private void translate2(View view) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 1f);
		animation.setDuration(500);
		animation.setFillAfter(true);
		animation.setInterpolator(new DecelerateInterpolator(0.3f));

		view.startAnimation(animation);
	}
}
