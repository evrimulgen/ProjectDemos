package com.projects.demo.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.projects.demo.R;
import com.projects.demo.view.MyView;

public class JieTuActivity extends Activity implements OnTouchListener{
	private int x;//绘画开始的横坐标
	private int y;//绘画开始的纵坐标
	private int m;//绘画结束的横坐标
	private int n;//绘画结束的纵坐标
	private int width;//绘画的宽度
	private int height;//绘画的高度
	private Bitmap bitmap;//生成的位图
	private MyView myView;//绘画选择区域
	private ImageView image1;
	private ImageView image2;
	private Button button;
	private LinearLayout linear;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jie_tu);
        linear = (LinearLayout) findViewById(R.id.linear);
        
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        myView = new MyView(this);
        button = (Button) findViewById(R.id.btnscreen);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(myView.isSign()){
					myView.setSeat(0, 0, 0, 0);
					myView.setSign(false);
					button.setText("停止截屏");
				}else{
					myView.setSign(true);
					button.setText("开始截屏");
					image2.setImageBitmap(null);
				}
				myView.postInvalidate();
			}
		});
        image1.setOnTouchListener(this);
        this.addContentView(myView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			x = 0;
			y = 0;
			width = 0;
			height = 0;
			x = (int) event.getX();
			y = (int) event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			m = (int) event.getX();
			n = (int) event.getY();
			myView.setSeat(x, y, m, n);
			myView.postInvalidate();
		}
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(event.getX()>x){
				width = (int)event.getX()-x;
			}else{
				width = (int)(x-event.getX());
				x = (int) event.getX();
			}
			if(event.getY()>y){
				height = (int) event.getY()-y;
			}else{
				height = (int)(y-event.getY());
				y = (int) event.getY();
			}
			if(width > 0 && height > 0) {
				image2.setImageBitmap(getWholeScreenBitmap(this));
			}
		}
		if(myView.isSign()){
			return false;
		}else{
			return true;
		}
	}
	
	private Bitmap getBitmap(Activity activity) {
		/*View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();*/
		
		linear.setDrawingCacheEnabled(true);
		linear.buildDrawingCache();
		int[] location = new int[2];
		linear.getLocationOnScreen(location);
		
		bitmap = linear.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int toHeight = frame.top;
		bitmap = Bitmap.createBitmap(bitmap, x-location[0], y+2*toHeight-location[1], width, height);
		try {
			FileOutputStream fout = new FileOutputStream("mnt/sdcard/test.png");
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		linear.setDrawingCacheEnabled(false);
		return bitmap;
	}
	
	private Bitmap getWholeScreenBitmap(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		
		bitmap = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int toHeight = frame.top;
		bitmap = Bitmap.createBitmap(bitmap, x, y+2*toHeight, width, height);
		try {
			FileOutputStream fout = new FileOutputStream("mnt/sdcard/test.png");
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.setDrawingCacheEnabled(false);
		
		return getRoundedCornerBitmap(bitmap);
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) { 
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), 
		bitmap.getHeight(), Config.ARGB_8888); 
		Canvas canvas = new Canvas(output); 

		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
		final RectF rectF = new RectF(rect); 
		final float roundPx = bitmap.getWidth() / 2; 

		paint.setAntiAlias(true); 
		canvas.drawARGB(0, 0, 0, 0); 
		paint.setColor(color); 
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, rect, rect, paint); 
		return output; 
	} 
}