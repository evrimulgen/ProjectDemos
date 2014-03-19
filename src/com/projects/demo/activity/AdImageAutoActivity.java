package com.projects.demo.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.adapter.AdImageAdapter;
import com.projects.demo.util.Env;
import com.projects.demo.view.GuideGallery;

public class AdImageAutoActivity extends Activity {

	private static final String tag = "AdImageAutoActivity";

	public List<String> urls;
	public GuideGallery images_ga;
	private LinearLayout pointLinear;
	private int size;
	private int gallery_positon = 0;

	private Timer autoGallery = new Timer();
	public boolean timeFlag = true;
	public ImageTimerTask timeTaks = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ad_images);
		initList();
		initView();

		timeTaks = new ImageTimerTask();
		autoGallery.scheduleAtFixedRate(timeTaks, 2000, 2000);
	}

	private void initList() {
		urls = new ArrayList<String>();
		urls.add("http://img4.ddimg.cn/00131/pic/mf_548x177_cx20120717.jpg");
		urls.add("http://img4.ddimg.cn/00131/pic/b5-0713-02.gif");
		urls.add("http://img4.ddimg.cn/00131/pic/b4-0718-01.jpg");
		urls.add("http://img4.ddimg.cn/00131/pic/hbsj_548x177_cx20120716.jpg");
		urls.add("http://img4.ddimg.cn/00131/pic/718-dd-548-177.jpg");
		urls.add("http://img4.ddimg.cn/00087/huwei/xrqc548x177_hw120713.jpg");

		size = urls.size();
	}

	private void initView() {
		images_ga = (GuideGallery) findViewById(R.id.image_wall_gallery);
		images_ga.setImageActivity(this);
		AdImageAdapter imageAdapter = new AdImageAdapter(urls, this);
		images_ga.setAdapter(imageAdapter);
		pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		pointLinear.setBackgroundColor(Color.argb(200, 135, 135, 152));
		for (int i = 0, size = urls.size(); i < size; i++) {
			ImageView pointView = new ImageView(this);
			if (i == 0) {
				pointView.setBackgroundResource(R.drawable.feature_point_cur);
			} else
				pointView.setBackgroundResource(R.drawable.feature_point);
			pointLinear.addView(pointView);
		}
		images_ga
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (Env.DEBUG)
							Log.d(tag, "Gallery onItemSelected, position = "
									+ position);
						changePointView(position % size);
						gallery_positon = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		images_ga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (Env.DEBUG) Log.d(tag, "Gallery onItemClick, position = " + position);
				Toast.makeText(AdImageAutoActivity.this, "click " + position, Toast.LENGTH_SHORT).show();
			}
		});
		images_ga.setSelection(1000 * size, true);
	}

	public void changePointView(int cur) {
		for (int i = 0; i < size; i++) {
			View imageView = pointLinear.getChildAt(i);
			if (i == cur) {
				imageView.setBackgroundResource(R.drawable.feature_point_cur);
			} else {
				imageView.setBackgroundResource(R.drawable.feature_point);
			}
		}
	}

	final Handler autoGalleryHandler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			switch (message.what) {
			case 1:
				imageViewOutAniamtion(images_ga.getSelectedView());
				images_ga.setSelection(gallery_positon + 1, true);
				imageViewInAniamtion(images_ga.getSelectedView());
				break;
			}
		}
	};

	public void imageViewOutAniamtion(View view) {
		view.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.right_left_out));
	}

	public void imageViewInAniamtion(View view) {
		view.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.right_left_in));
	}

	class ImageTimerTask extends TimerTask {
		public void run() {
			if (timeFlag) {
				autoGalleryHandler.sendEmptyMessage(1);
			}
		}
	}

	@Override
	public void finish() {
		autoGallery.cancel();
		super.finish();
	}
}
