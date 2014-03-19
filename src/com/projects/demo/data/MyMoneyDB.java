package com.projects.demo.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyMoneyDB extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 1; 

	private Context moneyContext;
	private  SQLiteDatabase moneyDatabase;
	private static  String DB_PATH = "/data/data/com.projects.demo/databases/";
	private static String DB_NAME = "mymoney_demo.sqlite";
	private static String TABLE_NAME = "t_account";
	
	public MyMoneyDB(Context context) { 
        super(context, DB_NAME, null, DATABASE_VERSION); 
        moneyContext = context;
    }
	
	public void openDataBase() throws SQLException {
	    //Open the database
	    String myPath = DB_PATH + DB_NAME;
	    moneyDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	@Override
	public synchronized void close() {
	
        if (moneyDatabase != null) {
        	moneyDatabase.close();
        }
        super.close();
	
	}
	
	public Cursor getAccount() {
		Cursor cursor = moneyDatabase.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	    //db.execSQL("Insert Into Question(_id,level,text,idTopic) Values (1,1,'asa',0)");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}

}