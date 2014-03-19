package com.projects.demo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.projects.demo.R;
import com.projects.demo.cache.BindDataIf.BindHolder;
import com.projects.demo.cache.BindDataIf.Callback;
import com.projects.demo.cache.DataManagerFactory;

public class FlingGalleryImageAdapter extends BaseAdapter {
	
	private static final String tag = "FlingGalleryImageAdapter";

	private List<String> imageUrls;
	private Context context;

	public FlingGalleryImageAdapter(List<String> imageUrls, Context context) {
		this.imageUrls = imageUrls;
		this.context = context;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return imageUrls.get(position % imageUrls.size());
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {  
		Log.d(tag, "FlingGalleryImageAdapter getView, position = " + position);
		/*ViewHolder holder;
		if(convertView==null) {  
            convertView = LayoutInflater.from(context).inflate(R.layout.flinggallery_item, null); //ÊµÀý»¯convertView  
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
            holder.imageView.setImageResource(R.drawable.image_loading);
            holder.textView = (TextView) convertView.findViewById(R.id.gallery_text);
            convertView.setTag(holder);
        } else {
    	   holder = (ViewHolder) convertView.getTag();
        }
        final ImageView temp = holder.imageView;
		DataManagerFactory.newInstance().bindData(context, new BindHolder(BindHolder.TYPE_IMAGE, imageUrls.get(position % imageUrls.size())), new Callback() {
			@Override
			public void callback(BindHolder holder) {
				Bitmap bitmap = (Bitmap)holder.getResource();
				if(null != bitmap) {
					temp.setImageBitmap(bitmap);
				}
			}
		}, true);*/
        final ImageView imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.image_loading);
        imageView.setLayoutParams(new Gallery.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
        DataManagerFactory.newInstance().bindData(context, new BindHolder(BindHolder.TYPE_IMAGE, imageUrls.get(position % imageUrls.size())), new Callback() {
			@Override
			public void callback(BindHolder hold) {
				Bitmap bitmap = (Bitmap)hold.getResource();
				if(null != bitmap) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}, true);
        return imageView;
    }
	
	static class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
	
	static class ViewHolder2 {
		ImageView imageView;
	}
}
