package com.projects.demo.activity;

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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.projects.demo.R;

public class CycleJieTuActivity extends Activity implements OnTouchListener{
	
	private static final String tag = "CycleJieTuActivity";
	
	private int x;//绘画开始的横坐标
	private int y;//绘画开始的纵坐标
	private int m;//绘画结束的横坐标
	private int n;//绘画结束的纵坐标
	private int width;//绘画的宽度
	private int height;//绘画的高度
	private Bitmap bitmap;//生成的位图
	//private MyView myView;//绘画选择区域
	private ImageView image1;
	private ImageView image2;
	private Button button;
	private LinearLayout linear;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_jie_tu);
        linear = (LinearLayout) findViewById(R.id.linear);
        
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        //myView = new MyView(this);
        button = (Button) findViewById(R.id.btnscreen);
        /*button.setOnClickListener(new OnClickListener() {
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
		});*/
        image1.setOnTouchListener(this);
        //this.addContentView(myView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			x = (int) event.getX();
			y = (int) event.getY();
			image2.setImageBitmap(getWholeScreenBitmap(this));
			Log.d(tag, "x=" + x + ", y=" + y);
		} else if(event.getAction() == MotionEvent.ACTION_MOVE){
			x = (int) event.getX();
			y = (int) event.getY();
			image2.setImageBitmap(getWholeScreenBitmap(this));
			Log.d(tag, "x=" + x + ", y=" + y);
		}
		return true;
	}
	
	private Bitmap getWholeScreenBitmap(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		
		bitmap = view.getDrawingCache();
		
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int toHeight = frame.top;
		
		view.setDrawingCacheEnabled(false);
		
		return getRoundedCornerBitmap(bitmap2, x, y+2*toHeight, 180);
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int x, int y, int radius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		//final Rect rect = new Rect(0, 0, (int)(x+radius*Math.sqrt(2)), (int)(y+radius*Math.sqrt(2))); 
		final Rect rect = new Rect(x-radius, y-radius, x+radius, y+radius);
		final RectF rectF = new RectF(rect); 

		paint.setAntiAlias(true); 
		canvas.drawARGB(0, 0, 0, 0); 
		paint.setColor(color); 
		canvas.drawRoundRect(rectF, radius, radius, paint); 

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	} 
}