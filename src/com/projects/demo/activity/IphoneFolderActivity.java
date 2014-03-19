package com.projects.demo.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.projects.demo.R;
import com.projects.demo.util.FolderCoverView;
import com.projects.demo.util.ImageUtil;

public class IphoneFolderActivity extends Activity {
	private final static int MSG_CLOSE_FOLDER = 100;
	private ArrayList<FolderCoverView> mFolderCovers;
	FolderCoverView upCover, downCover;
	RelativeLayout mDragLayer;
	LinearLayout ll;
	Bitmap mCurrentPageBitmap, mCurrentFolderBitmap;
	Button btn;
	int folderW = 0;
	int folderY = 0;
	boolean isShow = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.iphone_folder_main);
		initView();
		getBitmap();
	}

	/**
	 * ��ȡ����ͼƬ
	 */
	private void getBitmap() {
		// ��ȡ��Ļ����
		Rect rect = new Rect();
		View view = getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		mCurrentPageBitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.default_homebg);
		mCurrentPageBitmap = Bitmap.createBitmap(mCurrentPageBitmap, 0, 0, rect
				.width(), rect.height());
		BitmapDrawable bd = new BitmapDrawable(this.getResources(),
				mCurrentPageBitmap);
		mDragLayer.setBackgroundDrawable(bd);
	}

	/**
	 * ��Щ������ʼ��
	 */
	private void initView() {
		mDragLayer = (RelativeLayout) this.findViewById(R.id.phone);
		ll = (LinearLayout) this.findViewById(R.id.ll);
		ll.setBackgroundColor(Color.BLACK);
		btn = (Button) this.findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isShow = true;
				ll.setVisibility(View.VISIBLE);
				createFoldercoverView();
				SlideFolder();
			}
		});
	}

	/**
	 * �����ļ�����Ч����
	 * 
	 * @param folderInfo
	 */
	private void createFoldercoverView() {
		mFolderCovers = new ArrayList<FolderCoverView>();
		mCurrentFolderBitmap = ImageUtil.view2Bitmap(btn);
		upCover = new FolderCoverView(this, mCurrentPageBitmap, 0,
				mCurrentPageBitmap.getWidth(), btn.getBottom() + 30,
				mCurrentFolderBitmap, btn.getLeft(), btn.getTop(), 0);

		mFolderCovers.add(upCover);// ��ӵ����ϣ�����ر�ʱ�Ƴ�

		FrameLayout.LayoutParams upFlp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);

		mDragLayer.addView(upCover, upFlp);// ��ӵ�������

		downCover = new FolderCoverView(this, mCurrentPageBitmap, btn
				.getBottom() + 30, mCurrentPageBitmap.getWidth(),
				mCurrentPageBitmap.getHeight() - btn.getBottom() - 30, null, 0,
				0, 0);
		mFolderCovers.add(downCover);

		FrameLayout.LayoutParams downFlp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
		mDragLayer.addView(downCover, downFlp);// ��ӵ�������

	}

	/**
	 * ��ʼ����
	 */
	public void SlideFolder() {
		// ���ϻ���230----Y��
		upCover.slideBy(0, 0, 0, 200, FolderCoverView.SCROLL_CLOSE_DURATION);
		// ���»���60----Y��
		downCover.slideBy(0, 0, 0, -200, FolderCoverView.SCROLL_CLOSE_DURATION);
	}

	/**
	 * �ر���Ч
	 */
	public void closeFolderCoverView() {
		for (final FolderCoverView view : mFolderCovers) {
			view.slideBy(0, view.getDeltay(), 0, -view.getDeltay(),
					FolderCoverView.SCROLL_CLOSE_DURATION);
		}
		handler.sendEmptyMessageDelayed(MSG_CLOSE_FOLDER,
				FolderCoverView.SCROLL_CLOSE_DURATION - 300);
	}

	/**
	 * �Ƴ����������ѵ�VIEW
	 */
	public void removeCoverView() {
		for (final FolderCoverView view : mFolderCovers) {
			mDragLayer.removeView(view);
		}
	}

	/**
	 * ��дBack��
	 */
	@Override
	public void onBackPressed() {
		if (isShow) {
			closeFolderCoverView();
			isShow = false;
		} else {
			super.onBackPressed();
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_CLOSE_FOLDER:
				removeCoverView();
				ll.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};
}