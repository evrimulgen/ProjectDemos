package com.projects.demo.cache;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.projects.demo.cache.BindDataIf.BindHolder;
import com.projects.demo.cache.BindDataIf.Callback;

/**
 * download images.
 * this class include memory image cache, file object cache, and the files in the sdcard.
 * @Create 2012-05-18
 */
public class ImageDownloader {

	private static final String LOG_TAG = ImageDownloader.class.getSimpleName();
	
	private ExecutorService executorService;
	ImageMemoryCache imageMemoryCache;
	ImageFileCache imageFileCache;
	
	private static ImageDownloader instance;

	public static ImageDownloader getInstance(Context context) {
		if (instance == null) {
			synchronized(ImageDownloader.class) {
				if (instance == null)
					instance = new ImageDownloader(context);
			}
		}
		return instance;
	}
	
	private ImageDownloader(Context context) {
		executorService = Executors.newFixedThreadPool(10);//�̳߳�
		imageMemoryCache = new ImageMemoryCache();
		imageFileCache = new ImageFileCache(context);
	}
	
	/**
     * Download the specified image from the Internet and binds it to the provided ImageView. The
     * binding is immediate if the image is found in the cache and will be done asynchronously
     * otherwise. A null bitmap will be associated to the ImageView if an error occurs.
     *
     * @param url The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
	public void download(BindHolder holder, Callback callback) {
		String url = holder.getUrl();
		//imageMemoryCache.resetPurgeTimer();
		Bitmap bitmap = imageMemoryCache.getBitmapFromCache(url);
		if(bitmap == null) {
			executorService.submit(new ImageTask(new ImageTaskHandler(callback), url, holder));
		} else {
			holder.setResource(bitmap);
			callback.callback(holder);
		}
	}
	
	/**
     * get one image from three places, imageMemoryCache,imageFileCache,network
     *
     * @param url The URL of the image to download.
     * @return Bitmap return a bitmap
     */
    private Bitmap getBitmap(final String url) {
        // get image from imageMemoryCache
        Bitmap result = imageMemoryCache.getBitmapFromCache(url);
        if (result == null) {
            // get image from imageFileCache
            result = imageFileCache.getImage(url);
            if (result == null) {
                // get image from network
                result = getImageHttp(url);
                if (result != null) {
                	imageMemoryCache.addBitmapToCache(url, result);
					imageFileCache.addImgToSDTask(url, result);
                }
            } else {
                // add bitmap to imageMemoryCache.
            	imageMemoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }
	
	
	/**
	 * @param url
	 * @return Bitmap from http
	 */
	private Bitmap getImageHttp(String url) {
		//try to get image from file cache
		Bitmap bitmap;
		int times = 0;
		while (times<3) {
			try {
				URL u = new URL(url);
				HttpURLConnection conn  = (HttpURLConnection)u.openConnection();
		        conn.setDoInput(true);
		        conn.connect();
		        InputStream inputStream = conn.getInputStream();
		        bitmap = BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
				inputStream.close();
		        return bitmap;
			} catch (Exception e) {
				Log.w(LOG_TAG, "end downloadBitmap, I/O error while retrieving bitmap from " + url, e);
				times ++;
			}
			continue;
		}
		return null;
	} // end of downloadBitmap
	
	/*
     * 
     */
	/**
	 * An InputStream.
	 * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
	 * @Create Date:2012-05-18
	 */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    } // end of FlushedInputStream
    
    /**
     * a handler. 
	 * <p>receive message from , then update imageView.</p>
     * @Create Date:2012-05-18
     */
    private class ImageTaskHandler extends Handler {
        BindDataIf.Callback callback;

        public ImageTaskHandler(BindDataIf.Callback callback) {
        	this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
            	BindHolder holder = (BindHolder) msg.obj;
                callback.callback(holder);
            }
        }
    }

    /**
     * a callable class
	 * ExecutorService can execute it's objects, get image from three places.
     * @Create Date:2012-05-18
     */
    private class ImageTask implements Callable<String> {
        private String url;
        private Handler handler;
        private BindHolder holder;

        public ImageTask(Handler handler, String url, BindHolder holder) {
            this.url = url;
            this.handler = handler;
            this.holder = holder;
        }

        @Override
        public String call() throws Exception {
            Message msg = new Message();
            holder.setResource(getBitmap(url));
            msg.obj = holder;
            if (msg.obj != null) {
                handler.sendMessage(msg);
            }
            return url;
        }
    }
}
