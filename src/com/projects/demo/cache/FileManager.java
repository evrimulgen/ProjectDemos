package com.projects.demo.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.projects.demo.util.Env;

/**
 * download xml,json, and some static methods about operating files.
 * @Create 2012-05-25
 */
public class FileManager {
	
	public static final int DATA_SIZE_IN_MB = 2;
	public static final int SD_SIZE_IN_MB = 10;
	private static final long XML_JSON_TIME_DIFF = 3 * 24 * 60 * 60 * 1000;	//the file will be deleted after TIME_DIFF(unit ms).
	public static final long IMAGE_TIME_DIFF = 3 * 24 * 60 * 60 * 1000;	//the file will be deleted after TIME_DIFF(unit ms).
	public static int MB = 1024*1024;
	
	/**
	 * get string from local file, or download network file and return a string from file.
	 * @param 
	 * @return String a string from xml or json file.
	 */
	public static String getStringFromFile(Context context, String appName, String url, String fileNameToSave) {
		String resultString = null;
		try {
			resultString = getStringFromLocal(context, appName, fileNameToSave);
			if(resultString == null) {
				resultString = getStringFromUrlFile(context, appName, url,fileNameToSave);
			}
		} catch (IOException e) {
			resultString = null;
		}
		return resultString;
	}
	
	/**
	 * get a inputStream from local file, or download network file and return a inputStream from file.
	 * @param 
	 * @return InputStream a inputStream from xml or json file.
	 */
	public static InputStream getInputStreamFromFile(Context context, String appName, String url, String fileNameToSave) {
		InputStream resultInputStream = null;
		try {
			resultInputStream = getInputStreamFromLocal(context, appName, fileNameToSave);
			if(resultInputStream == null) {
				resultInputStream = getFileFromUrl(context, appName, url, fileNameToSave);
			}
		} catch (IOException e) {
			resultInputStream = null;
		}
		return resultInputStream;
	}
	
	private static InputStream getInputStreamFromLocal(Context context, String appName, String fileNameToSave) 
			throws IOException {
		File file = new File(Env.getDataDirectoryByAppName(appName), fileNameToSave);
		if(!file.exists()) {
			return null;
		}
		return new FileInputStream(file);
	}
	
	private static String getStringFromLocal(Context context, String appName, String fileNameToSave) 
			throws IOException {
		File file = new File(Env.getDataDirectoryByAppName(appName), fileNameToSave);
		if(!file.exists()) {
			return null;
		}
		updateFileTime(file);
		FileInputStream fis = new FileInputStream(file);
		return convertStreamToString(fis);
	}
	
	private static InputStream getFileFromUrl(Context context, String appName, String url, String fileNameToSave)
	{
		InputStream bis = download(url);
		if(bis == null) {
			return null;
		}
		
		if (canWriteData()) {
			reduceCache(Env.getAppDataDirectory(), DATA_SIZE_IN_MB, 
					freeSpaceOnData(Environment.getDataDirectory().getPath()), XML_JSON_TIME_DIFF);
		} else {
			return bis;
		}
		
		File file = new File(Env.getDataDirectoryByAppName(appName), fileNameToSave);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			return bis;
		}
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		try {
    		writeFile(bis, bos);
			fos.close();
		} catch (IOException e1) {
			file.delete();
		}
		
		return bis;
	}
	
	private static String getStringFromUrlFile(Context context, String appName, String url, String fileNameToSave)
	{
		InputStream bis = getFileFromUrl(context, appName, url, fileNameToSave);
		return convertStreamToString(bis);
	}
	
	private static InputStream download(String url) {
		InputStream is = null;
		BufferedInputStream bis = null;
		try 
		{
			URLConnection conn = new URL(url).openConnection();
			conn.setReadTimeout(0);
			conn.connect();
		
			is = conn.getInputStream();
			bis = new BufferedInputStream(is, 8192);
		} catch (Exception e) {
			is = null;
		}
		return bis;
	}
	
	public static boolean canWriteData() {
		long folderSizeInMb = getFileSize(Env.getAppDataDirectory()) / MB;
		return folderSizeInMb < DATA_SIZE_IN_MB;
	}
	
	public static boolean canWriteSD() {
		long folderSizeInMb = 0;
		if(exsitSdcard()) {
			folderSizeInMb = getFileSize(Env.getExternalStorageDirectory()) / MB;
			return folderSizeInMb < SD_SIZE_IN_MB;
		} else {
			return false;
		}
	}
	
	private static void writeFile(InputStream bis, OutputStream bos) throws IOException {
		int r = 0;
		byte[] buffer = new byte[2048];
		while ((r = bis.read(buffer)) != -1) {
			bos.write(buffer, 0 , r);
		}
		
		bos.flush();
	}
	
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
	
	/** 
	 * count the size of the directory, when the size is larger than CACHE_SIZE ,
	 * or the rest room of sdcard is smaller the FREE_SD_SPACE_TO_CACHE, delete the 40% files 
	 * which were not lately used. 
	 * @param dirPath 
	 * @param filename 
	 */ 
	public static void reduceCache(File dir, int maxMB, int freeSpaceOnDataOrSd, long time_diff) {
	    File[] files = dir.listFiles();
	    if (files == null) {
	        return;
	    }
	    int dirSize = 0;
	    for (int i = 0; i < files.length;i++) {
	    	if(!removeExpiredCache(files[i], time_diff))
	    		dirSize += files[i].length();
	    }
	    if (dirSize > maxMB * MB || maxMB > freeSpaceOnDataOrSd) {
	        int removeFactor = (int) ((0.4 *files.length));
	        Arrays.sort(files, new FileLastModifComparator());
	        for (int i = 0; i <removeFactor; i++) {
	        	files[i].delete();
	        }
	    }
	}
	
	/**
     * get folder size
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        File fList[] = file.listFiles();
        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isDirectory()) {
                size = size + getFileSize(fList[i]);
            } else {
                size = size + fList[i].length();
            }
        }
        return size;
    }
    
    /**
     * judge the sdcard is available or not
     * @return
     */
    public static boolean exsitSdcard() {
    	boolean ret = false ;
        String sDcString = Environment.getExternalStorageState();    
        if (sDcString.equals(Environment.MEDIA_MOUNTED)) { 
           ret = true ;
        }
        return ret;
    }
	
    public static int freeSpaceOnData(String path) {
	    StatFs stat = new StatFs(path);
	    double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
	    return (int) sdFreeMB;
	}
	
	/** 
	 * remove expired files
	 * @param dirPath 
	 * @param filename 
	 */ 
    public static boolean removeExpiredCache(File file, long time_diff) {
	    if (System.currentTimeMillis() - file.lastModified() > time_diff) {
	        return file.delete();
	    }
	    return false;
	}
    
    /**
	 * clear file cache on disk.
	 */
	public static void deleteAll(File dir) {
		File[] files = dir.listFiles();
        if(files == null)
            return;
        for(File f:files) {
        	if(f.isDirectory()) {
        		deleteAll(f);
        		f.delete();
        	} else {
        		f.delete();
			}
        }
	}
	
	/** 
	 * update the LastModified time of the file
	 * @param dir 
	 * @param fileName
	 */ 
    public static void updateFileTime(File file) {
	    long newModifiedTime = System.currentTimeMillis();
	    file.setLastModified(newModifiedTime);
	}
	
	/** 
     * file comparator
     */ 
    public static class FileLastModifComparator implements Comparator<File> {
        @Override
		public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;  
            } else {
                return -1;  
            }
        }
    }
}
