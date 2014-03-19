package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.projects.demo.R;
import com.projects.demo.listener.MultiPointTouchListener;

public class MultiTouchImageViewActivity extends Activity {

	private ImageView touchImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multitouch_image_main);
		touchImageView = (ImageView) findViewById(R.id.touchImageView);
		touchImageView.setOnTouchListener(new MultiPointTouchListener(touchImageView));
		touchImageView.setScaleType(ScaleType.CENTER_INSIDE);
	}

	
}
