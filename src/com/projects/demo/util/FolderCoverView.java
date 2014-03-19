package com.projects.demo.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.Scroller;

import com.projects.demo.R;

/**
 * 文件夹特效类
 * 
 * @author liaonaibo
 * 
 */
public class FolderCoverView extends View {
	private Bitmap  mCurrentPageBitmap;
	private Bitmap mFolderBitmap;
	private int mWidth;
	private int mHeight;
	private int mTop;
	private int mFolderLeft;
	private int mFolderTop;
	private Paint transPaint;
	private Paint mPaint;
	private Scroller mScroller;
	private ArrayList<Bitmap> mRecycleBitmaps;
	private int mDeltay;
	public static final int SCROLL_CLOSE_DURATION = 2000;

	/**
	 * 文件夹特效类
	 * 
	 * @param context
	 * @param currentPageBitmap
	 * @param top
	 * @param width
	 *            根据手机屏幕尺寸决定
	 * @param height
	 *            根据view为upCover还是downCover来设定，upCover:屏幕顶部到文件夹图标的底部高度；downCover:
	 * @param currentFolderBitmap
	 * @param iconLeft
	 * @param iconTop
	 */
	public FolderCoverView(Context context, Bitmap currentPageBitmap, int top,
			int width, int height, Bitmap currentFolderBitmap, int iconLeft,
			int iconTop, int wallpaperTop) {
		super(context);
		mCurrentPageBitmap = currentPageBitmap;
//		mScreenBitmap = Bitmap.createBitmap(currentPageBitmap, 0, top, width,
//				height);
		mFolderBitmap = currentFolderBitmap;
		mWidth = width;
		mHeight = height;
		mTop = top;
		mScroller = new Scroller(context);

		mFolderLeft = iconLeft;
		mFolderTop = iconTop;

		mRecycleBitmaps = new ArrayList<Bitmap>();
		mRecycleBitmaps.add(currentPageBitmap);

	}

	@Override
	public void draw(Canvas canvas) {
		transPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		transPaint.setAlpha(0x88);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(getContext().getResources().getColor(
				R.color.delete_color_filter));
		mPaint.setAlpha(0xff);

		// 画壁纸
		canvas.drawBitmap(createCurrentWallpaper(mCurrentPageBitmap), 0, mTop,
				null);

		// 画桌面item
//		canvas.drawBitmap(mScreenBitmap, 0, mTop, transPaint);

		// 只针对upCover
		if (mFolderBitmap != null) {

			// 画箭头和文件夹
			Bitmap arrow = createArrowBitmap();
			canvas.drawBitmap(mFolderBitmap, mFolderLeft, mFolderTop, null);

			
			canvas.drawBitmap(arrow, mFolderLeft
					+ mFolderBitmap.getWidth()
					/ 2
					- (int) getResources().getDimension(
							R.dimen.saf_folder_cover_arrow_width) / 2, mHeight
					- arrow.getHeight(), null);
		}
	}

	/**
	 * 将界面内容进行滚动
	 * 
	 * @param startx
	 *            Starting horizontal scroll offset in pixels. Positive numbers
	 *            will scroll the content to the left.
	 * @param starty
	 *            Starting vertical scroll offset in pixels. Positive numbers
	 *            will scroll the content up.
	 * @param deltax
	 *            Horizontal distance to travel. Positive numbers will scroll
	 *            the content to the left.
	 * @param deltay
	 *            Vertical distance to travel. Positive numbers will scroll the
	 *            content up.
	 * @param Duration
	 *            of the scroll in milliseconds.
	 */
	public void slideBy(int startx, int starty, int deltax, int deltay,
			int duration) {
		mDeltay = deltay;
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}

		mScroller.startScroll(startx, starty, deltax, deltay, duration);
		this.invalidate();
	}

	@Override
	public void computeScroll() {

		if (mScroller.computeScrollOffset()) {
			int desY = mScroller.getCurrY();
			this.scrollTo(0, desY);

			postInvalidate();

		} else {
			super.computeScroll();
		}
	}

	public Bitmap createCurrentWallpaper(Bitmap currentPageBitmap) {

		Bitmap coverBg = Bitmap.createBitmap(currentPageBitmap, 0, mTop,
				mWidth, mHeight);
		return coverBg;
	}

	public Bitmap createArrowBitmap() {
		Bitmap arrow = ImageUtil.createBitmapThumbnail(
				BitmapFactory.decodeResource(getResources(),
						R.drawable.saf_folder_indicator), (int) getResources()
						.getDimension(R.dimen.saf_folder_cover_arrow_width),
				(int) getResources().getDimension(
						R.dimen.saf_folder_cover_arrow_height));
		// 记得回收bitmap
		return arrow;
	}

	public void recycleAll() {
		for (Bitmap garbage : mRecycleBitmaps) {
			garbage.recycle();
		}

		mRecycleBitmaps.clear();
		mRecycleBitmaps = null;
	}

	public int getDeltay() {
		return mDeltay;
	}

}
