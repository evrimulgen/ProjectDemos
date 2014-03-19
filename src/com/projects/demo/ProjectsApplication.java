package com.projects.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.projects.demo.data.MyMoneyDB;

public class ProjectsApplication extends Application {

	private MyMoneyDB myMoneyDb;
	
	private static  String DB_PATH = "/data/data/com.projects.demo/databases/";
	private static String DB_NAME = "mymoney_demo.sqlite";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		createDataBase();
		/*Intent intent = new Intent("com.projects.demo.provider.aidl.AIDLService");
		startService(intent);*/
	}
	
	public void createDataBase() {
	    boolean dbExist = checkDataBase();
	    if (dbExist) {
	        //do nothing - database already exist
	    } else {
	        try {
	            copyDataBase();
	        } catch (IOException e) {
	            throw new Error("Error copying database");
	        }
	    }
	}
	
	private boolean checkDataBase() {
	    SQLiteDatabase checkDB = null;
	    try {
	        String myPath = DB_PATH + DB_NAME;
	        File f = new File(DB_PATH);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

	    } catch (SQLiteException e) {
	        return false;
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	    	if (checkDB != null) {
	    		checkDB.close();
	    	}
	    }
	    return checkDB != null ? true : false;
	}
	
	private void copyDataBase() throws IOException {
	    //Open your local db as the input stream
	    InputStream myInput = getApplicationContext().getAssets().open(DB_NAME);
	
	    // Path to the just created empty db
	    //String outFileName = DB_PATH + DB_NAME;
	
	    String outFileName = DB_PATH + DB_NAME;
	    //Open the empty db as the output stream
	    OutputStream myOutput = new FileOutputStream(outFileName);
	
	    //transfer bytes from the inputfile to the outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = myInput.read(buffer)) > 0) {
	        myOutput.write(buffer, 0, length);
	    }
	
	    //Close the streams
	    myOutput.flush();
	    myOutput.close();
	    myInput.close();
	
	}

}
