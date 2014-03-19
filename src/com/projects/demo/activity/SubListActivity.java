package com.projects.demo.activity;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.demo.adapter.MyExpandableListAdapter;
  
public class SubListActivity extends ExpandableListActivity { 
  
    private ExpandableListAdapter mAdapter; 
  
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        this.setTitle("ExpandableList"); 
        mAdapter = new MyExpandableListAdapter(this); 
        setListAdapter(mAdapter); 
        registerForContextMenu(this.getExpandableListView()); 
    } 
  
    // Ϊ�б��ÿһ��������Ĳ˵����������� �����Ĳ˵��� 
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, 
            ContextMenuInfo menuInfo) { 
        menu.setHeaderTitle("ContexMenu"); 
        menu.add(0, 0, 0, "ContextMenu"); 
    } 
  
    // ���������Ĳ˵�����߼� 
    @Override
    public boolean onContextItemSelected(MenuItem item) { 
  
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item 
                .getMenuInfo(); 
        String title = ((TextView) info.targetView).getText().toString(); 
  
        int type = ExpandableListView 
                .getPackedPositionType(info.packedPosition); 
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) { 
  
            int groupPos = ExpandableListView 
                    .getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView 
                    .getPackedPositionChild(info.packedPosition); 
            Toast.makeText( 
                    this, 
                    title + "-Group Index" + groupPos + "Child Index:"
                            + childPos, Toast.LENGTH_SHORT).show(); 
            return true; 
        } 
        return false; 
    } 
}
