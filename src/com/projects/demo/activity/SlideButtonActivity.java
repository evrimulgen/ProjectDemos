package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.view.SlideButtonView;
import com.projects.demo.view.SlideButtonView.OnSwitchListener;

public class SlideButtonActivity extends Activity {
    
	private SlideButtonView slipswitch_MSL;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_button);
        
        slipswitch_MSL = (SlideButtonView)findViewById(R.id.main_myslipswitch);
        slipswitch_MSL.setImageResource(R.drawable.bkg_switch, R.drawable.bkg_switch, R.drawable.btn_slip);
        slipswitch_MSL.setSwitchState(true);
        slipswitch_MSL.setOnSwitchListener(new OnSwitchListener() {
			
			@Override
			public void onSwitched(boolean isSwitchOn) {
				// TODO Auto-generated method stub
				if(isSwitchOn) {
					Toast.makeText(SlideButtonActivity.this, "开关已经开启", 300).show();
				} else {
					Toast.makeText(SlideButtonActivity.this, "开关已经关闭", 300).show();
				}
			}
		});
    }
}