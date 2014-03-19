package com.projects.demo.cache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.projects.demo.util.Env;

/**
 * image file cache in sdcard.
 * this class includes the files in the sdcard, and writes bitmap to sdcard.
 * @Create Date:2012-05-18
 */
public class ImageFileCache {
	
	private static final String LOG_TAG = ImageFileCache.class.getSimpleName();
	
	private static final String IMG_SUFFIX = ".cach";
	
	private File cacheDir;
	
	/** producer and consumer */
	private BlockingQueue<UrlAndBitmap> bitmapWriteQueue;
	private Thread writeThread = null;
	/**
	 * @param context
	 */
	public ImageFileCache(Context context) {
		cacheDir = Env.getImageCacheDirectory(context);
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();//create all directory.
		}
		bitmapWriteQueue = new LinkedBlockingQueue<UrlAndBitmap>();	//FIFO, write bitmap to sdcard
		writeThread = new Thread(writeRunnable);
		writeThread.start();
	}
	
	/**
	 * get image from 
	 * @param url the url of image.
	 * @return Bitmap if a image existed in the sdcard,then return a bitmap.If didn't existed,return null.
	 */
	public Bitmap getImage(String url) {
        File file = getFromFileCache(url);
        if (file.exists()) {
            Bitmap bmp;
			try {
				bmp = BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				bmp = null;
			}
            if(bmp == null) {
                file.delete();
            } else {
            	FileManager.updateFileTime(file);
            	Log.i(LOG_TAG, "get Bitmap From ImageFileCache, url=" + url);
                return bmp;
            }
        }
        return null;
    }
	
	/**
	 * consume.
	 * consume a task from bitmapWriteQueue.
	 * @param url hashcode
	 * @return the string of url encoded by hashcode
	 */
	private Runnable writeRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				while (true) {
					UrlAndBitmap urlAndBitmap = bitmapWriteQueue.take();
					saveImgToSD(urlAndBitmap.url, urlAndBitmap.bitmap);
				}
			} catch (InterruptedException e) {
				Log.i(LOG_TAG, "the thread of writeRunnable is interrupted.");
			}
		}
	};
	
	/**
	 * produce.
	 * produce a task to bitmapWriteQueue.
	 * @param url hashcode
	 * @return the string of url encoded by hashcode
	 */
	public void addImgToSDTask(String url, Bitmap bitmap){
		bitmapWriteQueue.add(new UrlAndBitmap(url, bitmap));
	}
	
	/**
	 * save image to sdcard.
	 * @param url hashcode
	 * @return the string of url encoded by hashcode
	 */
	public void saveImgToSD(String url, Bitmap bitmap){
		if (FileManager.canWriteSD()) {
			FileManager.reduceCache(cacheDir, FileManager.SD_SIZE_IN_MB, 
					FileManager.freeSpaceOnData(Environment.getExternalStorageDirectory().getPath()), FileManager.IMAGE_TIME_DIFF);//clear file cache
		} else {
			return ;
		}
		
		File file = getFromFileCache(url);
		if(file.exists()){
			file.delete();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			boolean isSaved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			if(isSaved) {
			}
			bos.close();
		} catch (FileNotFoundException e) {
			Log.w(LOG_TAG, "failed to save image to sd. url=" + url, e);
		} catch (IOException e) {
			Log.w(LOG_TAG, "failed to save image to sd. url=" + url, e);
		}
	}
	
	/**
	 * get the file object from file cache.
	 */
	public File getFromFileCache(String url) {
		String fileName = urlToFileName(url);
		File file = new File(cacheDir, fileName + IMG_SUFFIX);
		return file;
	}
	
	/**
	 * 
	 * @param url hashcode
	 * @return the string of url encoded by hashcode
	 */
	private String urlToFileName(String url) {
		return String.valueOf(url.hashCode());
	}
	
	@SuppressWarnings("unused")
	private void copyStream(InputStream is, OutputStream os){
        final int buffer_size = 4096;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	
    class UrlAndBitmap {
    	String url;
    	Bitmap bitmap;
    	public UrlAndBitmap(String url, Bitmap bitmap) {
    		this.url = url;
    		this.bitmap = bitmap;
    	}
    }
}
