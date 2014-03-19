package com.projects.demo.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

import com.projects.demo.R;

public class FontActivity extends Activity {

	private EditText locationEditText;
	private EditText locationWebEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.font_layout_main);
		locationEditText = (EditText) findViewById(R.id.locationEditText);
		locationWebEditText = (EditText) findViewById(R.id.locationWebEditText);
		
		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/STXingkai.ttf");
		locationEditText.setTypeface(face);
		Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/digital-7.ttf");
		locationWebEditText.setTypeface(face2);
	}

}
