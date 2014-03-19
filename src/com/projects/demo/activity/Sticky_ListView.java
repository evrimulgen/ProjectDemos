package com.projects.demo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.projects.demo.R;
import com.projects.demo.adapter.Sticky_Adapter;
import com.projects.demo.view.sticky.master.StickyListHeadersListView;
import com.projects.demo.view.sticky.master.StickyListHeadersListView.OnHeaderClickListener;

public class Sticky_ListView extends SherlockActivity implements OnScrollListener, AdapterView.OnItemClickListener, OnHeaderClickListener 
{
	private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";
	private int firstVisible;
	private Sticky_Adapter adapter;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setTitle("Sticky ListView");
		setContentView(R.layout.listview_sticky_layout);

		StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.listview_sticky_listview);
		stickyList.setOnScrollListener(this);
		stickyList.setOnItemClickListener(this);
		stickyList.setOnHeaderClickListener(this);

		if (savedInstanceState != null) 
		{
			firstVisible = savedInstanceState.getInt(KEY_LIST_POSITION);
		}

		stickyList.addHeaderView(getLayoutInflater().inflate(R.layout.horizontal_scroll_view, null));
		stickyList.addFooterView(getLayoutInflater().inflate(R.layout.listview_sticky_list_footer,  null));
		stickyList.setEmptyView(findViewById(R.id.listview_sticky_empty));
		adapter = new Sticky_Adapter(this);
		stickyList.setAdapter(adapter);
		stickyList.setSelection(firstVisible);
		stickyList.setDrawingListUnderStickyHeader(true);

		new Handler().postDelayed(new Runnable() 
		{
			@Override
			public void run()
			{
				adapter.notifyDataSetChanged();
			}
		}, 5000);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_LIST_POSITION, firstVisible);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
	{
		this.firstVisible = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) 
	{
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Toast.makeText(this, "Item " + position + " Clicked!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky)
	{
		//Toast.makeText(this, "Header " + headerId, Toast.LENGTH_SHORT).show();
	}
}