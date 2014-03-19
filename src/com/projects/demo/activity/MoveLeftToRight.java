package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.projects.demo.R;

public class MoveLeftToRight extends Activity {

	LinearLayout move1;
	LinearLayout move2;
	LinearLayout move3;
	ImageView moveImage4;
	ImageView moveImage5;
	ImageView moveImage6;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.move_left_right);

		initView();
		runAnimation(move1, new MyThread(2));
		runAnimation3(move2);
		runAnimation(move3, new MyThread(1));
		runAnimation3(moveImage4);
		
		runAnimation3(moveImage6);
	}

	private void initView() {
		move1 = (LinearLayout) findViewById(R.id.move1);
		move2 = (LinearLayout) findViewById(R.id.move2);
		move3 = (LinearLayout) findViewById(R.id.move3);
		moveImage4 = (ImageView) findViewById(R.id.moveImage4);
		moveImage5 = (ImageView) findViewById(R.id.moveImage5);
		moveImage5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				runAnimation5(moveImage5);
			}
		});
		moveImage6 = (ImageView) findViewById(R.id.moveImage6);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				runAnimation2(move3, false);
				break;
			case 2:
				runAnimation2(move1, true);
				break;
			}
			super.handleMessage(msg);
		}
	};

	class MyThread extends Thread {
		private int msgId;
		public MyThread(int msgId) {
			this.msgId = msgId;
		}
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			Message message = new Message();
			message.what = msgId;
			handler.sendMessage(message);
		}
	}

	private void runAnimation(View view, final Thread thread) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		animation.setDuration(2000);
		animation.setFillAfter(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				thread.start();
			}
		});

		view.startAnimation(animation);
	}

	private void runAnimation2(final View view, final boolean disappear) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f);
		animation.setDuration(2000);
		animation.setFillAfter(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if(disappear) {
					view.setVisibility(View.GONE);
				}
			}
		});

		view.startAnimation(animation);
	}
	
	private void runAnimation3(View view) {
		TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		animation.setDuration(2000);
		animation.setFillAfter(true);

		view.startAnimation(animation);
	}
	
	private void runAnimation5(View view) {
		ScaleAnimation animation = new ScaleAnimation(0f, 1f,
				0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(800);

		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(2400);
		
		animationSet.addAnimation(animation);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setFillBefore(false);
		animationSet.setFillAfter(true);
		
		view.startAnimation(animationSet);
		
		ScaleAnimation animation2 = new ScaleAnimation(0f, 1f,
				0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
		animation2.setDuration(500);
	}
}
