package com.projects.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;

import com.projects.demo.R;
  
public class GifActivity extends Activity { 
    private MyCustomView mView; 
    private Movie mMovie; 
    private long mMovieStart; 
  
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        mView = new MyCustomView(this); 
        setContentView(mView); 
    } 
  
    // �Զ���һ���࣬�̳�View 
    class MyCustomView extends View { 
        public MyCustomView(Context context) { 
            super(context); 
            // ���ļ����ķ�ʽ��ȡ�ļ� 
            mMovie = Movie.decodeStream(getResources().openRawResource( 
                    R.drawable.my_picture)); 
        } 
  
        @Override
        protected void onDraw(Canvas canvas) { 
            long curTime = android.os.SystemClock.uptimeMillis(); 
            // ��һ�β��� 
            if (mMovieStart == 0) { 
                mMovieStart = curTime; 
            } 
  
            if (mMovie != null) { 
                int duration = mMovie.duration(); 
  
                int relTime = (int) ((curTime - mMovieStart) % duration); 
                mMovie.setTime(relTime); 
                mMovie.draw(canvas, 0, 0); 
  
                // ǿ���ػ� 
                invalidate(); 
            } 
            super.onDraw(canvas); 
        } 
    } 
}
