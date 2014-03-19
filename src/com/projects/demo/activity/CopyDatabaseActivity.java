package com.projects.demo.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.projects.demo.R;
import com.projects.demo.data.MyMoneyDB;

public class CopyDatabaseActivity extends Activity {

	private TextView accountTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copy_database_demo_main);
		accountTextView = (TextView) findViewById(R.id.accountTextView);
		
		getAccount();
	}
	
	private void getAccount() {
		MyMoneyDB money = new MyMoneyDB(this);
		money.openDataBase();
		StringBuffer buf = new StringBuffer();
		
		Cursor cursor = money.getAccount();
		while (cursor.moveToNext()) {
			buf.append("id:" + cursor.getLong(cursor.getColumnIndex("accountPOID")) + ";name:" 
					+ cursor.getString(cursor.getColumnIndex("name")));
		}
		
		accountTextView.setText(buf.toString());
		cursor.close();
		money.close();
	}
	
}
