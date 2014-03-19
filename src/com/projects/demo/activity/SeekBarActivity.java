package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.projects.demo.R;

public class SeekBarActivity extends Activity {
	
	private static final String tag = "SeekBarActivity";
	
	SeekBar sound;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.seekbar);
        
        sound = (SeekBar)findViewById(R.id.sound);
        sound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				Log.d(tag, "progress = " + progress);
			}
		});
	}
}
