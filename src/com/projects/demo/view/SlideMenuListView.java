package com.projects.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SlideMenuListView extends ListView implements OnGestureListener {

	private GestureDetector gd;
	// �¼�״̬
	public static final char FLING_CLICK = 0;
	public static final char FLING_LEFT = 1;
	public static final char FLING_RIGHT = 2;
	public static char flingState = FLING_CLICK;

	private float distanceX;// ˮƽ�����ľ���

	private MyListViewFling myListViewFling;

	public static boolean isClick = false;// �Ƿ���Ե��

	public void setMyListViewFling(MyListViewFling myListViewFling) {
		this.myListViewFling = myListViewFling;
	}

	public float getDistanceX() {
		return distanceX;
	}

	public char getFlingState() {
		return flingState;
	}

	private Context context;

	public SlideMenuListView(Context context) {
		super(context);

	}

	public SlideMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		gd = new GestureDetector(this);
	}

	/**
	 * ��д�˷������Խ��ListView��������������
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		myListViewFling.doFlingOver(event);// �ص�ִ�����.
		this.gd.onTouchEvent(event);

		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		/***
		 * ���ƶ���ʱ��
		 */
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
			isClick = true;
		if (ev.getAction() == MotionEvent.ACTION_MOVE)
			isClick = false;
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		int position = pointToPosition((int) e.getX(), (int) e.getY());
		if (position != ListView.INVALID_POSITION) {
			View child = getChildAt(position - getFirstVisiblePosition());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// �󻬶�
		if (distanceX > 0) {
			flingState = FLING_RIGHT;
			Log.v("jj", "��distanceX=" + distanceX);
			myListViewFling.doFlingLeft(distanceX);// �ص�
			// �һ���.
		} else if (distanceX < 0) {
			flingState = FLING_LEFT;
			Log.v("jj", "��distanceX=" + distanceX);
			myListViewFling.doFlingRight(distanceX);// �ص�
		}

		return false;
	}

	/***
	 * �����Ĳ˵�
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		// System.out.println("Listview long press");
		// int position = pointToPosition((int) e.getX(), (int) e.getY());
		// if (position != ListView.INVALID_POSITION) {
		// View child = getChildAt(position - getFirstVisiblePosition());
		// if (child != null) {
		// showContextMenuForChild(child);
		// this.requestFocusFromTouch();
		// }
		//
		// }
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		return false;
	}

	/***
	 * �ص��ӿ�
	 * 
	 * @author jjhappyforever...
	 * 
	 */
	public interface MyListViewFling {
		void doFlingLeft(float distanceX);// �󻬶�ִ��

		void doFlingRight(float distanceX);// �һ���ִ��

		void doFlingOver(MotionEvent event);// ��ק�ɿ�ʱִ��

	}

}
