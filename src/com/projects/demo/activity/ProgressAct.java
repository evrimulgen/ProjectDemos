package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.projects.demo.util.BeanUtils;
import com.projects.demo.util.UnitUtil;

public class ProgressAct extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //setContentView(R.layout.progress);
		
		ProgressBar progressBar = new ProgressBar(this);
		BeanUtils.setFieldValue(progressBar, "mOnlyIndeterminate", new Boolean(false));
		progressBar.setIndeterminate(false);
        progressBar.setProgressDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
        progressBar.setIndeterminateDrawable(getResources().getDrawable(android.R.drawable.progress_indeterminate_horizontal));
        //progressBar.setMinimumHeight(UnitUtil.dip2px(this, 20));
        
        LinearLayout layout = new LinearLayout(this);
        layout.addView(progressBar, new LayoutParams(LayoutParams.FILL_PARENT, UnitUtil.dip2px(this, 20)));
        setContentView(layout);
        
        progressBar.setSecondaryProgress(50);
	}
}
