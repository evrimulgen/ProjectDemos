package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projects.demo.R;
import com.projects.demo.api.ApiService;

public class SSLActivity extends Activity implements View.OnClickListener {
	
	private static final int HANDLE_REGISTER_CALL_BACK = 1;
	private static final int HANDLE_GET_CATEGORY_CALL_BACK = 2;
	
	private Button mRequestBtn;
	private TextView mResponseTextView;
	
	private ApiService apiService = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.ssl_main_layout);
        apiService = ApiService.getInstance();
        initViews();
	}
	
	private void initViews() {
		mRequestBtn = (Button) findViewById(R.id.requestButton);
		mRequestBtn.setOnClickListener(this);
		mResponseTextView = (TextView) findViewById(R.id.responseTextView);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.requestButton:
			new RegisterTerminalThread().start();
			break;

		default:
			break;
		}
	}
	
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLE_REGISTER_CALL_BACK:
				String registerStr = (String) msg.obj;
				mResponseTextView.setText(registerStr);
				new GetCategoryThread(registerStr).start();
				break;
			case HANDLE_GET_CATEGORY_CALL_BACK:
				String categoryStr = (String) msg.obj;
				mResponseTextView.setText(categoryStr);
				break;

			default:
				break;
			}
		}
		
	};
	
	class RegisterTerminalThread extends Thread {

		@Override
		public void run() {
			try {
				String responseStr = apiService.register();
				Message msg = mHandler.obtainMessage();
				msg.what = HANDLE_REGISTER_CALL_BACK;
				msg.obj = responseStr;
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	class GetCategoryThread extends Thread {
		String terminalId;
		
		public GetCategoryThread(String terminalId) {
			this.terminalId = terminalId;
		}

		@Override
		public void run() {
			try {
				String responseStr = apiService.getCategoriesJson(terminalId).toString();
				Message msg = mHandler.obtainMessage();
				msg.what = HANDLE_GET_CATEGORY_CALL_BACK;
				msg.obj = responseStr;
				mHandler.sendMessageDelayed(msg, 5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
