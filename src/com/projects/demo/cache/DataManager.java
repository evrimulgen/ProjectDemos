package com.projects.demo.cache;

import java.io.InputStream;

import android.content.Context;

public class DataManager implements BindDataIf {

	@Override
	public void bindData(final Context context, final BindHolder holder, final Callback callback, boolean useNewThread) {
		int type = holder.getType();
		final String url = holder.getUrl();
		if(useNewThread) {
			if(type == BindHolder.TYPE_IMAGE) {
				ImageDownloader.getInstance(context).download(holder, callback);
			} else if(type == BindHolder.TYPE_JSON){
				new Thread(new Runnable() {
					@Override
					public void run() {
						String jsonString = FileManager.getStringFromFile(context, "app", url, holder.getSaveFileName());
						holder.setResource(jsonString);
						callback.callback(holder);
					}
				}).start();
				
			} else if(type == BindHolder.TYPE_XML){
				new Thread(new Runnable() {
					@Override
					public void run() {
						InputStream fis = FileManager.getInputStreamFromFile(context, "app", url, holder.getSaveFileName());
						holder.setResource(fis);
						callback.callback(holder);
					}
				}).start();
			} else {
				return ;
			}
		} else {
			if(type == BindHolder.TYPE_IMAGE) {
				InputStream fis = FileManager.getInputStreamFromFile(context, "app", url, holder.getSaveFileName());
				holder.setResource(fis);
				callback.callback(holder);
			} else if(type == BindHolder.TYPE_JSON){
				String jsonString = FileManager.getStringFromFile(context, "app", url, holder.getSaveFileName());
				holder.setResource(jsonString);
				callback.callback(holder);
			} else if(type == BindHolder.TYPE_XML){
				InputStream fis = FileManager.getInputStreamFromFile(context, "app", url, holder.getSaveFileName());
				holder.setResource(fis);
				callback.callback(holder);
			} else {
				return ;
			}
		}
	}
}
