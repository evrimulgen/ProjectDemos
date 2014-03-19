package com.projects.demo.util;

import android.util.Log;

public class Logger {
	
	/*static {
		String[] arrayOfString = new String[1];
		arrayOfString[0] = "Project Demos";
	}*/
	
	private static final boolean DEBUG = false;
//	private static final String[] PRINT_TAGS = arrayOfString;
	private static final boolean SHOW_ALL_LOG = false;
	private static final String TAG_DEFAULT = "Project Demos";

	/*private static boolean isInTags(String paramString) {
		boolean bool = false;
		if ((PRINT_TAGS == null) || (PRINT_TAGS.length == 0)) {
			return bool;
		}

		while (true) {
			
			String[] arrayOfString = PRINT_TAGS;
			int i = arrayOfString.length;
			for (int j = 0;; j++) {
				if (j >= i)
					break;
				if (arrayOfString[j].equals(paramString)) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}*/

	private static boolean isShowLog(String paramString) {
		return DEBUG;
	}

	public static void logD(Class<?> paramClass, String paramString) {
		logD(paramClass.getName(), paramString);
	}

	public static void logD(String paramString) {
		logD(TAG_DEFAULT, paramString);
	}

	public static void logD(String paramString1, String paramString2) {
		if (isShowLog(paramString1)) {
			Log.d(paramString1, paramString2);
		}
	}

	public static void logE(Class<?> paramClass, String paramString) {
		logE(paramClass.getName(), paramString);
	}

	public static void logE(String paramString) {
		logE(TAG_DEFAULT, paramString);
	}

	public static void logE(String paramString1, String paramString2) {
		if (isShowLog(paramString1)) {
			Log.e(paramString1, paramString2);
		}
	}

	public static void logE(String paramString1, String paramString2,
			Throwable paramThrowable) {
		if (isShowLog(paramString1)) {
			Log.e(paramString1, paramString2, paramThrowable);
		}
	}

	public static void logE(String paramString, Throwable paramThrowable) {
		logE(paramString, paramThrowable.getLocalizedMessage(), paramThrowable);
	}

	public static void logE(Throwable paramThrowable) {
		logE(TAG_DEFAULT, paramThrowable);
	}

	public static void logI(Class<?> paramClass, String paramString) {
		logI(paramClass.getName(), paramString);
	}

	public static void logI(String paramString) {
		logI(TAG_DEFAULT, paramString);
	}

	public static void logI(String paramString1, String paramString2) {
		if (isShowLog(paramString1)) {
			Log.i(paramString1, paramString2);
		}
	}

	public static void logV(Class<?> paramClass, String paramString) {
		logV(paramClass.getName(), paramString);
	}

	public static void logV(String paramString) {
		logV(TAG_DEFAULT, paramString);
	}

	public static void logV(String paramString1, String paramString2) {
		if (isShowLog(paramString1)) {
			Log.v(paramString1, paramString2);
		}
	}

	public static void logW(Class<?> paramClass, String paramString) {
		logW(paramClass.getName(), paramString);
	}

	public static void logW(String paramString) {
		logW(TAG_DEFAULT, paramString);
	}

	public static void logW(String paramString1, String paramString2) {
		if (isShowLog(paramString1)) {
			Log.w(paramString1, paramString2);
		}
	}

	public static void printEx(Exception paramException) {
		Log.e(TAG_DEFAULT, paramException.getMessage());
	}
}
