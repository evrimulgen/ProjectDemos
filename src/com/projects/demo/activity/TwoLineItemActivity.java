package com.projects.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

import com.projects.demo.R;
import com.projects.demo.adapter.TwoLineListItemContactArrayAdapter;
import com.projects.demo.entity.Contacts;
  
public class TwoLineItemActivity extends ListActivity { 
      
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setListAdapter(new TwoLineListItemContactArrayAdapter(this, R.layout.two_line_list_item_demo_main, getContacts())); 
    } 
  
    private List<Contacts> getContacts() { 
        List<Contacts> contacts = new ArrayList<Contacts>(); 
        Contacts c; 
        c = new Contacts(); 
        c.setName("Shriram"); 
        c.setPhone("123456"); 
  
        contacts.add(c); 
  
        c = new Contacts(); 
        c.setName("John"); 
        c.setPhone("456789"); 
        contacts.add(c); 
        return contacts; 
    } 
} 

