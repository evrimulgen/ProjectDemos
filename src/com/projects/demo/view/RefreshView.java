package com.projects.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.projects.demo.R;
import com.projects.demo.util.UnitUtil;

public class RefreshView extends LinearLayout {

	private Context mContext;
	
	public RefreshView(Context context) {
		super(context);
		mContext = context;
		
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(R.drawable.loading);
		addView(imageView, UnitUtil.dip2px(mContext, 60), UnitUtil.dip2px(mContext, 60));
		startAnimation(imageView);
	}
	
	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(R.drawable.loading);
		addView(imageView, UnitUtil.dip2px(mContext, 60), UnitUtil.dip2px(mContext, 60));
		startAnimation(imageView);
	}

	public void startAnimation(View view) {
		Animation am = new RotateAnimation(0, +360, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		am.setDuration(600);
		am.setRepeatCount(-1);
		am.setInterpolator(new LinearInterpolator());
		
		view.startAnimation(am);
	}
}
