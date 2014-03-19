package com.projects.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.projects.demo.R;

public class V4FragmentActivity extends FragmentActivity {
	
	private static final String tag = "V4FragmentActivity";
	
	ViewPager vp;
	Fragment testA, testB, testC;
	FragmentTransaction mTransaction;
	TextView[] labels = new TextView[3];
	TextView tv;
	static int WIDHT = 54;
	int left = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.v4frag);
		vp = (ViewPager) findViewById(R.id.pager);
		testA = new TestFragmentA();
		testB = new TestFragmentB();
		testC = new TestFragmentC();
		labels[0] = (TextView) findViewById(R.id.tv11);
		labels[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vp.setCurrentItem(0);
			}
		});
		labels[1] = (TextView) findViewById(R.id.tv12);
		labels[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vp.setCurrentItem(1);
			}
		});
		labels[2] = (TextView) findViewById(R.id.tv13);
		labels[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vp.setCurrentItem(2);
			}
		});

		tv = (TextView) findViewById(R.id.tv);
		vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

			public int getCount() {
				return 3;
			}

			public Fragment getItem(int position) {
				switch (position) {
					case 0:
						return testA;
					case 1:
						return testB;
					case 2:
						return testC;
				}
				return null;
			}
		});
		WIDHT = getWindowManager().getDefaultDisplay().getWidth() / labels.length;
		LayoutParams lp = (LayoutParams) tv.getLayoutParams();
		lp.width = WIDHT;
		tv.setLayoutParams(lp);
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				System.out.println("onPageSelected");
				setSelect(position);
				TranslateAnimation animation = new TranslateAnimation(left, position * WIDHT, 0, 0);
				animation.setDuration(200);
				animation.setFillAfter(true);
				animation.setFillBefore(true);
				final int i = position;
				animation.setAnimationListener(new AnimationListener() {

					public void onAnimationEnd(Animation animation) {
						Log.d(tag, "onAnimationEnd");
					}

					public void onAnimationRepeat(Animation animation) {

					}

					public void onAnimationStart(Animation animation) {
						Log.d(tag, "onAnimationStart");
						left = i * WIDHT;
					}
				});
				tv.startAnimation(animation);
			}

			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			public void onPageScrollStateChanged(int state) {

			}
		});
		setSelect(0);
	}

	private void setSelect(int idx) {
		for (int i = 0; i < 3; i++) {
			if (i == idx) {
				labels[i].setTextColor(Color.RED);
				// lines[i].setBackgroundColor(Color.RED);
			} else {
				labels[i].setTextColor(Color.GRAY);
				// lines[i].setBackgroundColor(Color.GRAY);
			}
		}
	}
}