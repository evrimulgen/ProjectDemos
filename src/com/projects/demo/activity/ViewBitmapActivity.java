package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.projects.demo.R;

public class ViewBitmapActivity extends Activity {

	private static final String tag = "ViewBitmapActivity";

	private Button button;
	private Button button2;
	private ImageView imgAddViewCache;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_bitmap);

		button = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button.setDrawingCacheEnabled(true);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imgAddViewCache = new ImageView(ViewBitmapActivity.this);

				Bitmap cacheBitmap = button.getDrawingCache();
				int color = button.getDrawingCacheBackgroundColor();
				Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);// 注意:这地方必须特别注意
				if (bitmap != null) {
					imgAddViewCache.setImageBitmap(bitmap);
					imgAddViewCache.setDrawingCacheBackgroundColor(color);
				} else {
					Log.i("CACHE_BITMAP", "DrawingCache=null");
				}
				
				showPopupWindow(button, button, 400, 200);
			}
		});
	}

	private void showPopupWindow(View parent, final View view3, int view2_width, int view2_height) {
		Log.d(tag, "onClick, showPopupWindow");
		AbsoluteLayout layout = new AbsoluteLayout(this);
		layout.setBackgroundColor(Color.RED);
		Button view2 = new Button(this);
		view2.setText("button");
		view2.setBackgroundColor(Color.WHITE);
		view2.setLayoutParams(new AbsoluteLayout.LayoutParams(view2_width, view2_height, 20, 20));
		view2.setVisibility(View.GONE);
		//layout.setGravity(Gravity.CENTER);
		layout.addView(view2);
		view2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(tag, "view2 setOnClickListener");
			}
		});
		
		PopupWindow mPopupWindow = new PopupWindow(layout,
				WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT, true);
		ColorDrawable dw = new ColorDrawable(Color.parseColor("#70000000"));
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setFocusable(true);
		/*layout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(screenSwitcher.mPopupWindow != null && screenSwitcher.mPopupWindow.isShowing()) {
					screenSwitcher.mPopupWindow.dismiss();
					screenSwitcher.mPopupWindow= null;
				}
				return false;
			}
		});*/
		mPopupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER, 0, 0);
		
		int[] location = new int[2];
		location[0] = 62;
		location[1] = 46;
		int[] location2 = new int[2];
		location2[0] = 440;
		location2[1] = 291;
		
		view2.startAnimation(getDialogAnimation(view2, (62-20)-(440-20), 0, (46-20)-(291-20), 0));
	}
	
	private Animation getDialogAnimation(final View view, float xStart, final float xDelta, float yStart,
			final float yDelta) {
		Log.d(tag, "xStart=" + xStart + ",yStart=" + yStart + ", xDelta=" + xDelta + ",yDelta=" + yDelta);
		AnimationSet animationSet = new AnimationSet(true);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f,
				0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);

		TranslateAnimation centerScaleAnimation = new TranslateAnimation(xStart, xDelta,
				yStart, yDelta);
		centerScaleAnimation.setDuration(1000);

		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(centerScaleAnimation);
		animationSet.setFillBefore(false);
		animationSet.setFillAfter(true);
		animationSet.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE);
				AbsoluteLayout.LayoutParams params1=new AbsoluteLayout.LayoutParams(400, 
                        200, 440, 291);
				view.setLayoutParams(params1);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//view.layout((int)xDelta, (int)yDelta, (int)xDelta, (int)yDelta);
				/*view.offsetLeftAndRight((int)xDelta);
				view.offsetTopAndBottom((int)yDelta);*/
			}
		});
		
		return animationSet;
	}
}
