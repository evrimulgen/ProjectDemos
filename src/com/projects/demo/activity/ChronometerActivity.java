package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import com.projects.demo.R;
  
public class ChronometerActivity extends Activity { 
    private Chronometer chronometer = null; 
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.chronometer_demo_main); 
  
        chronometer = (Chronometer) findViewById(R.id.chronometer); 
        chronometer.setFormat("¼ÆÊ±£º%s"); 
    } 
  
    public void onStart(View view) { 
        chronometer.start(); 
    } 
  
    public void onStop(View view) { 
        chronometer.stop(); 
    } 
  
    public void onReset(View view) { 
        chronometer.setBase(SystemClock.elapsedRealtime()); 
    } 
} 

