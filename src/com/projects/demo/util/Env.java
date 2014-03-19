package com.projects.demo.util;

import java.io.File;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.projects.demo.cache.FileManager;

public class Env {
	
	public static final boolean DEBUG = true;
	
	private static final String DATA_FILE = Environment.getDataDirectory().getPath() + "/data/com.projects.demo/files";
	private static final String SD_HOME = "demos";
	private static final String SD_IMG = "ImageCache";
	
	public static File getImageCacheDirectory(Context context) {
		File cacheDir;
		if(FileManager.exsitSdcard()) {
			cacheDir = new File(getExternalStorageDirectory(), SD_IMG);
		} else {
			cacheDir = context.getCacheDir();// /data/data/***.***.***/cache
		}
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();//create all directory.
		}
		return cacheDir;
	}
	
	public static File getDataDirectoryByAppName(String appName) {
		File appDir = new File(getAppDataDirectory(), appName);
		if(!appDir.exists()) {
			appDir.mkdirs();//create all directory.
		}
		return appDir;
	}
	
	public static File getExternalStorageDirectory() {
		return new File(Environment.getExternalStorageDirectory().getPath(), SD_HOME);
	}
	
	public static File getAppDataDirectory() {
		return new File(DATA_FILE);
	}
	
	public static boolean isJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean isHoneyComb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}
}
