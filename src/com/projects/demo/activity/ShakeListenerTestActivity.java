package com.projects.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.projects.demo.R;
import com.projects.demo.util.ShakeDetector;

public class ShakeListenerTestActivity extends Activity {
	private ShakeDetector mShaker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		

		final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

		mShaker = new ShakeDetector(this);
		mShaker.registerOnShakeListener(new ShakeDetector.OnShakeListener() {
			@Override
			public void onShake() {
				Log.d("shake", "shaking");
				final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vibe.vibrate(300);
			}
		});
		
		mShaker.start();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mShaker.stop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mShaker.stop();
	}
	
	
}


