package com.projects.demo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.demo.R;
import com.projects.demo.cache.BindDataIf.BindHolder;
import com.projects.demo.cache.BindDataIf.Callback;
import com.projects.demo.cache.DataManagerFactory;

public class AdImageAdapter extends BaseAdapter {

	private List<String> imageUrls;
	private Context context;

	public AdImageAdapter(List<String> imageUrls, Context context) {
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
		return position % imageUrls.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {  
		ViewHolder holder;
        if(convertView==null){  
            convertView = LayoutInflater.from(context).inflate(R.layout.ad_item, null); //ÊµÀý»¯convertView  
            Gallery.LayoutParams params = new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
            holder.textView = (TextView) convertView.findViewById(R.id.gallery_text);
            convertView.setTag(holder);
        }
       else{
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
		}, true);
        return convertView;
    }
	
	static class ViewHolder {
		ImageView imageView;
		TextView textView;
	}
}
