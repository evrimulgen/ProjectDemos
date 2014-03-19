package com.projects.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.projects.demo.R;
import com.projects.demo.adapter.ImageAdapter;
import com.projects.demo.view.GalleryFlow;

public class GalleryActivity extends Activity {
	
	private String strArray[];
	
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	           
        setContentView(R.layout.layout_gallery);
//        File file = new File(path);
//        str = file.list();	    
        
        strArray = new String[] {"g1.jpg", "g2.jpg", "g3.jpg", "g4.jpg", "g5.jpg"};
        ImageAdapter adapter = new ImageAdapter(this, strArray);
        adapter.createReflectedImages();//创建倒影效果
        GalleryFlow galleryFlow = (GalleryFlow) this.findViewById(R.id.Gallery01);
        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setSpacing(-100); //图片之间的间距
        galleryFlow.setAdapter(adapter);
       
        galleryFlow.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent();
                intent.putExtra("path", path+"/"+str[position]);
                intent.setClass(GalleryActivity.this, EditPhotoActivity.class);
                GalleryActivity.this.startActivity(intent);*/
            }
           
        });
        galleryFlow.setSelection(0);
    }
}

