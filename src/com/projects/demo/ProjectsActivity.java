package com.projects.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.projects.demo.activity.AbsoluteLayoutAct;
import com.projects.demo.activity.AdImageAutoActivity;
import com.projects.demo.activity.AndroidFileListenerActivity;
import com.projects.demo.activity.AnimationRightLeft;
import com.projects.demo.activity.ChronometerActivity;
import com.projects.demo.activity.CopyDatabaseActivity;
import com.projects.demo.activity.CycleJieTuActivity;
import com.projects.demo.activity.DialogAnimation;
import com.projects.demo.activity.FlingGalleryActivity;
import com.projects.demo.activity.FontActivity;
import com.projects.demo.activity.FrameLayoutTwoView;
import com.projects.demo.activity.GalleryActivity;
import com.projects.demo.activity.GifActivity;
import com.projects.demo.activity.GooglePlusActivity;
import com.projects.demo.activity.HorizontalScrollViewActivity;
import com.projects.demo.activity.IphoneFolderActivity;
import com.projects.demo.activity.IphoneTreeViewActivity;
import com.projects.demo.activity.JUnitTestActivity;
import com.projects.demo.activity.JieTuActivity;
import com.projects.demo.activity.ListViewActivity;
import com.projects.demo.activity.LocationActivity;
import com.projects.demo.activity.MergeIncludeActivity;
import com.projects.demo.activity.MoveLeftToRight;
import com.projects.demo.activity.MultiTouchImageViewActivity;
import com.projects.demo.activity.NotificationDemo;
import com.projects.demo.activity.OscilloscopeActivity;
import com.projects.demo.activity.PicCutActivity;
import com.projects.demo.activity.Progress2Act;
import com.projects.demo.activity.ProgressAct;
import com.projects.demo.activity.RecorderPlayActivity;
import com.projects.demo.activity.Refresh2;
import com.projects.demo.activity.SQLiteDatabaseDemoActivity;
import com.projects.demo.activity.SSLActivity;
import com.projects.demo.activity.SeekBarActivity;
import com.projects.demo.activity.ShakeListenerTestActivity;
import com.projects.demo.activity.ShortcutOptActivity;
import com.projects.demo.activity.SlideButtonActivity;
import com.projects.demo.activity.SlideMenuActivity;
import com.projects.demo.activity.SlidingDrawerActivity;
import com.projects.demo.activity.SpritzerActivity;
import com.projects.demo.activity.Sticky_ListView;
import com.projects.demo.activity.SubListActivity;
import com.projects.demo.activity.TitanicActivity;
import com.projects.demo.activity.TwoLineItemActivity;
import com.projects.demo.activity.V4FragmentActivity;
import com.projects.demo.activity.ViewBitmapActivity;
import com.projects.demo.activity.ViewPagerActivity;
import com.projects.demo.activity.VoiceRecognitionActivity;
import com.projects.demo.activity.WebViewActivity;
import com.projects.demo.activity.WebViewJSActivity;
import com.projects.demo.activity.XListViewActivity;
import com.projects.demo.cachevideo.CacheVideoPlayer;
import com.projects.demo.videoplayer.CopyOfVideoPlayerActivity;
import com.projects.demo.videoplayer.VideoPlayerActivity;
import com.projects.demo.videoplayer.VideoViewDemo;

public class ProjectsActivity extends ListActivity {
    
	private ListView mListView = null;
	private BaseAdapter adapter = null;
	private List<Map<String, Object>> mList = null;
	
	private static final String NAME = "name";
	private static final String CLASS = "class";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        
        initListView();
        initList();
        adapter = new SimpleAdapter(this, mList, android.R.layout.simple_list_item_1,
        		new String[]{NAME}, new int[]{android.R.id.text1});
        setListAdapter(adapter);
    }

	private void initListView() {
		mListView = getListView();
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Map<String, Object> map = (Map<String, Object>) mList.get(position);
				Intent intent = new Intent(ProjectsActivity.this, (Class<?>) map.get(CLASS));
				startActivity(intent);
			}
		});
	}
	
	private List<Map<String, Object>> initList() {
		mList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put(NAME, "refresh view animation");
		map2.put(CLASS, Refresh2.class);
		mList.add(map2);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(NAME, "ImageView move right from left");
		map.put(CLASS, AnimationRightLeft.class);
		mList.add(map);
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put(NAME, "image Ad move right from left");
		map3.put(CLASS, MoveLeftToRight.class);
		mList.add(map3);
		
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put(NAME, "SlidingDrawer");
		map4.put(CLASS, SlidingDrawerActivity.class);
		mList.add(map4);
		
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put(NAME, "ViewPager and Fragment");
		map5.put(CLASS, V4FragmentActivity.class);
		mList.add(map5);
		
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put(NAME, "switch Button");
		map6.put(CLASS, SlideButtonActivity.class);
		mList.add(map6);
		
		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put(NAME, "ad auto");
		map7.put(CLASS, AdImageAutoActivity.class);
		mList.add(map7);
		
		Map<String, Object> map8 = new HashMap<String, Object>();
		map8.put(NAME, "Fling Gallery, extends FrameLayout");
		map8.put(CLASS, FlingGalleryActivity.class);
		mList.add(map8);
		
		Map<String, Object> map9 = new HashMap<String, Object>();
		map9.put(NAME, "framelayout include two view");
		map9.put(CLASS, FrameLayoutTwoView.class);
		mList.add(map9);
		
		Map<String, Object> map10 = new HashMap<String, Object>();
		map10.put(NAME, "Android File Listener");
		map10.put(CLASS, AndroidFileListenerActivity.class);
		mList.add(map10);
		
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put(NAME, "Video Player");
		map11.put(CLASS, VideoPlayerActivity.class);
		mList.add(map11);
		
		Map<String, Object> map111 = new HashMap<String, Object>();
		map111.put(NAME, "Improve Media");
		map111.put(CLASS, CopyOfVideoPlayerActivity.class);
		mList.add(map111);
		
		Map<String, Object> map12 = new HashMap<String, Object>();
		map12.put(NAME, "VideoViewDemo");
		map12.put(CLASS, VideoViewDemo.class);
		mList.add(map12);
		
		Map<String, Object> map13 = new HashMap<String, Object>();
		map13.put(NAME, "SeekBar");
		map13.put(CLASS, SeekBarActivity.class);
		mList.add(map13);
		
		Map<String, Object> map14 = new HashMap<String, Object>();
		map14.put(NAME, "DialogAnimation");
		map14.put(CLASS, DialogAnimation.class);
		mList.add(map14);
		
		Map<String, Object> map15 = new HashMap<String, Object>();
		map15.put(NAME, "AbsoluteLayout includes FrameLayout");
		map15.put(CLASS, AbsoluteLayoutAct.class);
		mList.add(map15);
		
		Map<String, Object> map16 = new HashMap<String, Object>();
		map16.put(NAME, "ViewPager Activity");
		map16.put(CLASS, ViewPagerActivity.class);
		mList.add(map16);
		
		Map<String, Object> map17 = new HashMap<String, Object>();
		map17.put(NAME, "create bitmap from view");
		map17.put(CLASS, ViewBitmapActivity.class);
		mList.add(map17);
		
		Map<String, Object> map18 = new HashMap<String, Object>();
		map18.put(NAME, "¾ØÐÎ½ØÍ¼");
		map18.put(CLASS, JieTuActivity.class);
		mList.add(map18);
		
		Map<String, Object> map19 = new HashMap<String, Object>();
		map19.put(NAME, "Ô²ÐÎ½ØÍ¼");
		map19.put(CLASS, CycleJieTuActivity.class);
		mList.add(map19);
		
		Map<String, Object> map20 = new HashMap<String, Object>();
		map20.put(NAME, "Video Player with cache");
		map20.put(CLASS, CacheVideoPlayer.class);
		mList.add(map20);
		
		Map<String, Object> map21 = new HashMap<String, Object>();
		map21.put(NAME, "create progressBar manually");
		map21.put(CLASS, ProgressAct.class);
		mList.add(map21);
		
		Map<String, Object> map22 = new HashMap<String, Object>();
		map22.put(NAME, "create progressBar from xml");
		map22.put(CLASS, Progress2Act.class);
		mList.add(map22);
		
		Map<String, Object> map23 = new HashMap<String, Object>();
		map23.put(NAME, "Gif Demo");
		map23.put(CLASS, GifActivity.class);
		mList.add(map23);
		
		Map<String, Object> map24 = new HashMap<String, Object>();
		map24.put(NAME, "Pic Cut Demo");
		map24.put(CLASS, PicCutActivity.class);
		mList.add(map24);
		
		Map<String, Object> map25 = new HashMap<String, Object>();
		map25.put(NAME, "Sub List Demo");
		map25.put(CLASS, SubListActivity.class);
		mList.add(map25);
		
		Map<String, Object> map26 = new HashMap<String, Object>();
		map26.put(NAME, "VoiceRecognitionActivity");
		map26.put(CLASS, VoiceRecognitionActivity.class);
		mList.add(map26);
		
		Map<String, Object> map27 = new HashMap<String, Object>();
		map27.put(NAME, "Recorder Player");
		map27.put(CLASS, RecorderPlayActivity.class);
		mList.add(map27);
		
		Map<String, Object> map28 = new HashMap<String, Object>();
		map28.put(NAME, "Oscilloscope Demo");
		map28.put(CLASS, OscilloscopeActivity.class);
		mList.add(map28);
		
		Map<String, Object> map29 = new HashMap<String, Object>();
		map29.put(NAME, "Shake Demo");
		map29.put(CLASS, ShakeListenerTestActivity.class);
		mList.add(map29);
		
		Map<String, Object> map30 = new HashMap<String, Object>();
		map30.put(NAME, "Notification Demo");
		map30.put(CLASS, NotificationDemo.class);
		mList.add(map30);
		
		Map<String, Object> map31 = new HashMap<String, Object>();
		map31.put(NAME, "SQLite Database Demo");
		map31.put(CLASS, SQLiteDatabaseDemoActivity.class);
		mList.add(map31);
		
		Map<String, Object> map32 = new HashMap<String, Object>();
		map32.put(NAME, "Shortcut Demo");
		map32.put(CLASS, ShortcutOptActivity.class);
		mList.add(map32);
		
		Map<String, Object> map33 = new HashMap<String, Object>();
		map33.put(NAME, "Merge Include Demo");
		map33.put(CLASS, MergeIncludeActivity.class);
		mList.add(map33);
		
		Map<String, Object> map34 = new HashMap<String, Object>();
		map34.put(NAME, "TwoLineItem Demo");
		map34.put(CLASS, TwoLineItemActivity.class);
		mList.add(map34);
		
		Map<String, Object> map35 = new HashMap<String, Object>();
		map35.put(NAME, "Chronometer Demo");
		map35.put(CLASS, ChronometerActivity.class);
		mList.add(map35);
		
		Map<String, Object> map36 = new HashMap<String, Object>();
		map36.put(NAME, "Copy Database Demo");
		map36.put(CLASS, CopyDatabaseActivity.class);
		mList.add(map36);
		
		Map<String, Object> map37 = new HashMap<String, Object>();
		map37.put(NAME, "JUnit Test Demo");
		map37.put(CLASS, JUnitTestActivity.class);
		mList.add(map37);
		
		Map<String, Object> map38 = new HashMap<String, Object>();
		map38.put(NAME, "Slide Menu Demo");
		map38.put(CLASS, SlideMenuActivity.class);
		mList.add(map38);
		
		Map<String, Object> map39 = new HashMap<String, Object>();
		map39.put(NAME, "Iphone Folder Demo");
		map39.put(CLASS, IphoneFolderActivity.class);
		mList.add(map39);
		
		Map<String, Object> map40 = new HashMap<String, Object>();
		map40.put(NAME, "Multi Touch Image View Demo");
		map40.put(CLASS, MultiTouchImageViewActivity.class);
		mList.add(map40);
		
		Map<String, Object> map41 = new HashMap<String, Object>();
		map41.put(NAME, "Gallery Demo");
		map41.put(CLASS, GalleryActivity.class);
		mList.add(map41);
		
		Map<String, Object> map42 = new HashMap<String, Object>();
		map42.put(NAME, "WebViewActivity Demo");
		map42.put(CLASS, WebViewActivity.class);
		mList.add(map42);
		
		Map<String, Object> map43 = new HashMap<String, Object>();
		map43.put(NAME, "IphoneTreeView Demo");
		map43.put(CLASS, IphoneTreeViewActivity.class);
		mList.add(map43);
		
		Map<String, Object> map44 = new HashMap<String, Object>();
		map44.put(NAME, "XListView Demo");
		map44.put(CLASS, XListViewActivity.class);
		mList.add(map44);
		
		Map<String, Object> map45 = new HashMap<String, Object>();
		map45.put(NAME, "WebViewJSActivity Demo");
		map45.put(CLASS, WebViewJSActivity.class);
		mList.add(map45);
		
		Map<String, Object> map46 = new HashMap<String, Object>();
		map46.put(NAME, "SSLActivity Demo");
		map46.put(CLASS, SSLActivity.class);
		mList.add(map46);
		
		Map<String, Object> map47 = new HashMap<String, Object>();
		map47.put(NAME, "GooglePlusActivity Demo");
		map47.put(CLASS, GooglePlusActivity.class);
		mList.add(map47);
		
		Map<String, Object> map48 = new HashMap<String, Object>();
		map48.put(NAME, "HorizontalScrollView Demo");
		map48.put(CLASS, HorizontalScrollViewActivity.class);
		mList.add(map48);
		
		Map<String, Object> map49 = new HashMap<String, Object>();
		map49.put(NAME, "LocationActivity Demo");
		map49.put(CLASS, LocationActivity.class);
		mList.add(map49);
		
		Map<String, Object> map50 = new HashMap<String, Object>();
		map50.put(NAME, "ListViewActivity Demo");
		map50.put(CLASS, ListViewActivity.class);
		mList.add(map50);
		
		Map<String, Object> map51 = new HashMap<String, Object>();
		map51.put(NAME, "Sticky_ListView Demo");
		map51.put(CLASS, Sticky_ListView.class);
		mList.add(map51);
		
		Map<String, Object> map52 = new HashMap<String, Object>();
		map52.put(NAME, "FontActivity Demo");
		map52.put(CLASS, FontActivity.class);
		mList.add(map52);
		
		Map<String, Object> map53 = new HashMap<String, Object>();
		map53.put(NAME, "SpritzerActivity Demo");
		map53.put(CLASS, SpritzerActivity.class);
		mList.add(map53);
		
		Map<String, Object> map54 = new HashMap<String, Object>();
		map54.put(NAME, "TitanicActivity Demo");
		map54.put(CLASS, TitanicActivity.class);
		mList.add(map54);
		
		return mList;
	}
}