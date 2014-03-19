package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.projects.demo.R;

public class DialogAnimation extends Activity {

	ImageView imageView;
	PopupWindow popupWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_dialog);

		imageView = (ImageView) findViewById(R.id.image);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater) DialogAnimation.this
		                .getSystemService(LAYOUT_INFLATER_SERVICE);
		        View popupView = mLayoutInflater.inflate(
		                R.layout.popup_view, null);
				popupWindow = new PopupWindow(popupView,
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
						true);
				popupWindow.setOutsideTouchable(true);
				popupWindow.setFocusable(true);
				//popupWindow.setBackgroundDrawable(new BitmapDrawable());
				popupWindow.setBackgroundDrawable(new ColorDrawable(-00000));
		        
				//popupWindow.showAsDropDown(imageView, 0, 0);
				popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER
                        | Gravity.CENTER, 100, 100);
				popupWindow.setAnimationStyle(R.style.PopupAnimation);
				// 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
				popupWindow.update();
			}
		});
	}
}
