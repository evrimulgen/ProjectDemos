package com.projects.demo.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TwoLineListItem;

import com.projects.demo.entity.Contacts;
  
public class TwoLineListItemContactArrayAdapter extends ArrayAdapter<Contacts> { 
      
    private int resourceId; 
  
    public TwoLineListItemContactArrayAdapter(Context context, int textViewResourceId, 
            List<Contacts> object) { 
        super(context, textViewResourceId, object); 
        resourceId = textViewResourceId; 
    } 
  
    @Override
    public View getView(int position, View convertView, ViewGroup parent) { 
        Contacts contacts = getItem(position); 
        if (contacts == null) { 
            return null; 
        } 
        // 得到一个LayoutInflater实例 
        LayoutInflater inflater = (LayoutInflater) getContext() 
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        TwoLineListItem view; 
        if (convertView == null) { 
            view = (TwoLineListItem) inflater 
                    .inflate(resourceId, parent, false);// 由xml生成View 
        } else { 
            view = (TwoLineListItem) convertView; 
        } 
        if (view.getText1() != null) { 
            view.getText1().setText(contacts.getName()); 
        } 
        if (view.getText2() != null) { 
            view.getText2().setText(contacts.getPhone()); 
        } 
        return view; 
    } 
} 
