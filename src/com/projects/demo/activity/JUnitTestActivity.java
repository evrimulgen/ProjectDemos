package com.projects.demo.activity;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.AndroidTestRunner;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.junit.ExampleSuite;

public class JUnitTestActivity extends Activity implements View.OnClickListener {
	
	private Button launchButton;
	private TextView resultTextView;
	private TextView barTextView;
	private TextView messageTextView;
	
	private Thread testRunnerThread;
	
	private static final int SHOW_RESULT = 0;
	private static final int ERROR_FIND = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.junit_test_demo_main);
		
		launchButton = (Button) findViewById(R.id.launchButton);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		barTextView = (TextView) findViewById(R.id.barTextView);
		messageTextView = (TextView) findViewById(R.id.messageTextView);
		
		launchButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.launchButton:
			startTest();
			break;
		default:
			break;
		}
	}
	
	private synchronized void startTest() {
		if (testRunnerThread != null && testRunnerThread.isAlive()) {
			testRunnerThread = null;
		}
		if (testRunnerThread == null) {
			testRunnerThread = new Thread(new TestRunner(this));
			testRunnerThread.start();
		} else {
			Toast.makeText(this, "Test is still running", Toast.LENGTH_SHORT).show();            
		
		} 
	}
	
	public Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESULT:
				resultTextView.setText(msg.obj.toString());
				break;
			case ERROR_FIND:
				messageTextView.append(msg.obj.toString());
				barTextView.setBackgroundColor(Color.RED);
				break;
			default:
				break;
			}
		}
	};
	
	class TestRunner implements Runnable, TestListener {                    
		private Activity parentActivity;
		private int testCount;
		private int errorCount;
		private int failureCount;
		public TestRunner(Activity parentActivity) {
			this.parentActivity = parentActivity;
		}
		
		@Override
		public void run() {
			testCount = 0;
			errorCount = 0;
			failureCount = 0;
			ExampleSuite suite = new ExampleSuite();
			AndroidTestRunner testRunner = new AndroidTestRunner();
			testRunner.setTest(suite);
			testRunner.addTestListener(this);
			testRunner.setContext(parentActivity);
			testRunner.runTest();
		}
		
		@Override
		public void addError(Test test, Throwable t) {
			errorCount++;
			showMessage(t.getMessage() + "\n");
		}

		@Override
		public void addFailure(Test test, AssertionFailedError t) {
			failureCount++;
			showMessage(t.getMessage() + "\n");
		}
		
		@Override
		public void endTest(Test test) {
			showResult(getResult());
		}
	
		@Override
		public void startTest(Test test) {
			testCount++;

		}
		
		private String getResult() {
			int successCount = testCount - failureCount - errorCount;
			return "Test:" + testCount + " Success:" + successCount + " Failed:" + failureCount + " Error:" + errorCount;
		}                    
	}
	
	private void showMessage(String message) {
		hander.sendMessage(hander.obtainMessage(ERROR_FIND, message));
	
	}
	private void showResult(String text) {
		hander.sendMessage(hander.obtainMessage(SHOW_RESULT, text));

	}

	
}
