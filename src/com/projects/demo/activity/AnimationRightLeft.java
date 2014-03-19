package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.projects.demo.R;

public class AnimationRightLeft extends Activity {

	ImageView runImage;
	TranslateAnimation left, right;
	
	ImageView run_left, run_right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_right_left);

		initView();
		runAnimation();
	}

	private void initView() {
		runImage = (ImageView) findViewById(R.id.run_image);
		run_left = (ImageView) findViewById(R.id.run_left);
		run_left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runImage.startAnimation(left);
			}
		});
		
		run_right = (ImageView) findViewById(R.id.run_right);
		run_right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runImage.startAnimation(right);
			}
		});
	}

	private void runAnimation() {
		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		right.setDuration(3000);
		right.setFillAfter(true);
		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left.setDuration(3000);
		left.setFillAfter(true);
		left.setInterpolator(new AccelerateDecelerateInterpolator());
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
		
		runImage.startAnimation(right);
	}
}
