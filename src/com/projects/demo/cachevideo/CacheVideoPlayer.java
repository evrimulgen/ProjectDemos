package com.projects.demo.cachevideo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

import com.projects.demo.R;
  
public class CacheVideoPlayer extends Activity {  
    private SurfaceView surfaceView;  
    private Button btnPause, btnPlayUrl, btnStop;  
    private SeekBar skbProgress;  
    private VideoPlayer player;  
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.cache_video);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);  
  
        btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);  
        btnPlayUrl.setOnClickListener(new ClickEvent());  
  
        btnPause = (Button) this.findViewById(R.id.btnPause);  
        btnPause.setOnClickListener(new ClickEvent());  
  
        btnStop = (Button) this.findViewById(R.id.btnStop);  
        btnStop.setOnClickListener(new ClickEvent());  
  
        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);  
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());  
        player = new VideoPlayer(surfaceView, skbProgress);  
    }
  
    class ClickEvent implements OnClickListener {
  
        @Override  
        public void onClick(View arg0) {
            if (arg0 == btnPause) {  
                player.pause();  
            } else if (arg0 == btnPlayUrl) {
            	String url="http://192.168.1.201/Resources/c.mp4";  
                player.playUrl(url);
            } else if (arg0 == btnStop) {  
                player.stop();  
            }  
  
        }  
    }
    
    private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String url="http://192.168.1.201/Resources/c.mp4";  
            player.playUrl(url);
		}
    };
  
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {  
        int progress;  
  
        @Override  
        public void onProgressChanged(SeekBar seekBar, int progress,  
                boolean fromUser) {  
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()  
            this.progress = progress * player.mediaPlayer.getDuration()  
                    / seekBar.getMax();  
        }  
  
        @Override  
        public void onStartTrackingTouch(SeekBar seekBar) {  
  
        }  
  
        @Override  
        public void onStopTrackingTouch(SeekBar seekBar) {  
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字  
            player.mediaPlayer.seekTo(progress);  
        }  
    }  
  
}