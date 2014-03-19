package com.projects.demo.adapter;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.projects.demo.R;
import com.projects.demo.view.sticky.master.StickyListHeadersAdapter;

public class Sticky_Adapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer 
{
	private String[] countryArray;
	private ArrayList<String> sections;
	private LayoutInflater inflater;

	public Sticky_Adapter(Context context) 
	{
		inflater = LayoutInflater.from(context);
		countryArray = context.getResources().getStringArray(R.array.countries);
		Arrays.sort(countryArray);
		sections = new ArrayList<String>();
		for (String s : countryArray) 
		{
			if (!sections.contains("" + s.charAt(0))) 
			{
				sections.add("" + s.charAt(0));
			}
		}
	}

	@Override
	public int getCount()
	{
		return countryArray.length;
	}

	@Override
	public Object getItem(int position) 
	{
		return countryArray[position];
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;

		if (convertView == null) 
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listview_sticky_list_item, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.listview_sticky_list_item_text);
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(countryArray[position]);
		return convertView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) 
	{
		HeaderViewHolder holder;
		if (convertView == null) 
		{
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.listview_sticky_header, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.listview_sticky_headertext);
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (HeaderViewHolder) convertView.getTag();
		}
		String headerText = "" + countryArray[position].charAt(0);
		holder.text.setText(headerText);
		return convertView;
	}

	@Override
	public long getHeaderId(int position)
	{
		return countryArray[position].charAt(0);
	}

	class HeaderViewHolder 
	{
		TextView text;
	}

	class ViewHolder 
	{
		TextView text;
	}

	@Override
	public int getPositionForSection(int section)
	{
		if (section >= sections.size()) 
		{
			section = sections.size() - 1;
		}
		else if (section < 0) 
		{
			section = 0;
		}

		int position = 0;
		char sectionChar = sections.get(section).charAt(0);
		for (int i = 0; i < countryArray.length; i++) 
		{
			if (sectionChar == countryArray[i].charAt(0)) 
			{
				position = i;
				break;
			}
		}
		return position;
	}

	@Override
	public int getSectionForPosition(int position) 
	{
		if (position >= countryArray.length) 
		{
			position = countryArray.length - 1;
		} 
		else if (position < 0) 
		{
			position = 0;
		}
		return sections.indexOf("" + countryArray[position].charAt(0));
	}

	@Override
	public Object[] getSections() 
	{
		return sections.toArray(new String[sections.size()]);
	}

	public void clearAll() 
	{
		countryArray = new String[0];
		sections.clear();
	}
}
