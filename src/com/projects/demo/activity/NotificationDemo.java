package com.projects.demo.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.projects.demo.R;

public class NotificationDemo extends Activity implements View.OnClickListener {
	private Integer ID = 1000;
	private Button btnSend;
	private Button btnCancel;
	
	private NotificationManager notificationManager;
	private Notification notification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_demo);
		
		btnSend = (Button) findViewById(R.id.btnSendBroadcast);
		btnSend.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancelBroadcast);
		btnCancel.setOnClickListener(this);
		
		notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE); 
		notification = new Notification(R.drawable.ic_launcher, "信息", System.currentTimeMillis()); 

		
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.btnSendBroadcast:
			Intent intent = new Intent(NotificationDemo.this, NotificationDemo.class); 
	        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationDemo.this, 0, intent, 0); 
	        notification.setLatestEventInfo(NotificationDemo.this, "你的一条信息", "来自张三的信息", pendingIntent); 
	        notificationManager.notify(ID, notification); 
	        notificationManager.notify(ID+1, notification); 

			break;
		case R.id.btnCancelBroadcast:
			notificationManager.cancel(ID);
			notificationManager.cancel(ID + 1);
			break;

		default:
			break;
		}
	}

	
}
