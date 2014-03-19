package com.projects.demo.view;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import com.projects.demo.activity.AdImageAutoActivity;

public class GuideGallery extends Gallery {

	private long lastTime = 0;
	private static final int TIME_DIFF = 3000;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	
	private Context m_iact;
	public GuideGallery(Context context) {
		super(context);
	}
	
	public GuideGallery(Context context,AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	public GuideGallery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setImageActivity(Context iact){
		m_iact = iact;
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		int kEvent;
		//这样能够实现每次滑动只滚动一张图片的效果
        if(isScrollingLeft(e1, e2)){ //Check if scrolling left
          kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        }
        else{ //Otherwise scrolling right
          kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null); 
        long now = new Date().getTime();
        if(TIME_DIFF > now-lastTime) {//5s 内
        	timer.cancel();
        }
        ((AdImageAutoActivity)m_iact).timeFlag = false;
    	initTask();
		lastTime = now;
        return true;
	}
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
        return e2.getX() > e1.getX();
    }
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	private void initTask() {
		timer = new Timer();
		timerTask = new TimerTask(){
	       public void run() {
	    	   ((AdImageAutoActivity)m_iact).timeFlag = true;
	       }
	    };
		timer.schedule(timerTask, TIME_DIFF);
	}
}
