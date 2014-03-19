package com.projects.demo.activity;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class TestFragmentC extends Fragment implements Runnable {

	private static final String tag = "TestFragmentC";
	
	TextView tv;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		tv = new TextView(container.getContext());
		tv.setText(tag + " loading...");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(30);
		tv.setLayoutParams(new LayoutParams(-1, -1));
		// tv.setBackgroundColor(Color.BLUE);
		Log.d(tag, "onCreateView");
		new Thread(this).start();
		return tv;
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					tv.setText(tag + " get data successfully");
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(2);
			Log.d(tag, "getData");
		} catch (Exception e) {
		}
		handler.sendEmptyMessage(0);
	}
}
