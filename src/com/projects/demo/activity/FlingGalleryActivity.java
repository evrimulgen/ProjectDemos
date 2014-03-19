package com.projects.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.adapter.FlingGalleryImageAdapter;
import com.projects.demo.util.UnitUtil;
import com.projects.demo.view.FlingGallery;
import com.projects.demo.view.FlingGallery.OnGalleryChangeListener;

public class FlingGalleryActivity extends Activity {
	
	private static final String tag = "FlingGalleryActivity";
	
    public List<String> urls;
    private int size;
    
    private LinearLayout linearLayout;
    private FlingGallery gallery;  
    private LinearLayout pointLinear;
    
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.flinggallery);
       
        initList();
        initGallery();
        initView();
    }
    
    private void initGallery() {
    	linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
    	gallery = new FlingGallery(this);   
    	gallery.setPaddingWidth(UnitUtil.dip2px(this, 8));
        gallery.setBackgroundColor(Color.WHITE);  
        gallery.setAdapter(new FlingGalleryImageAdapter(urls, this));  
        gallery.addGalleryChangeListener(new OnGalleryChangeListener() {    
            @Override    
            public void onGalleryChange(int position) {    
                Log.v(tag, "onGalleryChange, position:"+position);
                changePointView(position % size);
            }    
        });
        gallery.setOnItemClick(new FlingGallery.OnItemClick() {
			@Override
			public void click(int position) {
				Log.v(tag, "setOnItemClick click, position:" + position);
				Toast.makeText(FlingGalleryActivity.this, "click " + position, Toast.LENGTH_SHORT).show();
			}
		});
        gallery.setLayoutParams(new LinearLayout.LayoutParams(UnitUtil.dip2px(this, 300), UnitUtil.dip2px(this, 100)));
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(gallery, 0);
    }

    private void initView() {
    		pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
    		pointLinear.setBackgroundColor(Color.argb(200, 135, 135, 152));
    		for (int i = 0; i < size; i++) {
    			ImageView pointView = new ImageView(this);
    			if (i == 0) {
    				pointView.setBackgroundResource(R.drawable.feature_point_cur);
    			} else
    				pointView.setBackgroundResource(R.drawable.feature_point);
    			pointLinear.addView(pointView);
    		}
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
} 
