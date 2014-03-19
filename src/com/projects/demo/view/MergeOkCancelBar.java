package com.projects.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.projects.demo.R;

public class MergeOkCancelBar extends LinearLayout { 
	  
    public MergeOkCancelBar(Context context) {
        super(context);
    }    
  
    public MergeOkCancelBar(Context context, AttributeSet attrs) { 
        super(context, attrs); 
  
        setOrientation(HORIZONTAL); 
        setGravity(Gravity.CENTER); 
        setWeightSum(1.0f); 
  
        LayoutInflater.from(context).inflate(R.layout.merge_ok_cancel_bar, this, true); 
  
        TypedArray array = context.obtainStyledAttributes(attrs, 
                R.styleable.MergeOkCancelBar, 0, 0); 
  
        String text = array.getString(R.styleable.MergeOkCancelBar_okLabel); 
        if (text == null) 
            text = "Ok"; 
        ((Button) findViewById(R.id.okcancelbar_ok)).setText(text); 
  
        text = array.getString(R.styleable.MergeOkCancelBar_cancelLabel); 
        if (text == null) 
            text = "Cancel"; 
        ((Button) findViewById(R.id.okcancelbar_cancel)).setText(text); 
  
        array.recycle(); 
    }
    
    public MergeOkCancelBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
    }
} 
