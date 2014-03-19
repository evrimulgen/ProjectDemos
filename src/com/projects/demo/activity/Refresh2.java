package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.projects.demo.R;
import com.projects.demo.view.RefreshView;

public class Refresh2 extends Activity {
	
	RefreshView refreshView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh2);
	}
}
