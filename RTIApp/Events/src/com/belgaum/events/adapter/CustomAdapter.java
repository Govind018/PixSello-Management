package com.belgaum.events.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belgaum.events.R;

public class CustomAdapter extends ArrayAdapter<String> {

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ArrayList<String> items;
	
	String typeOfScreen;

	public CustomAdapter(Context context, int resource,
			ArrayList<String> objects,String type) {
		super(context, resource, objects);
		thisContext = context;
		inflatableRes = resource;
		items = objects;
		typeOfScreen = type;
		infalter = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.image = (ImageView) convertView.findViewById(R.id.image);
		if(typeOfScreen.equalsIgnoreCase("Events")){
			holder.textName = (TextView) convertView.findViewById(R.id.text_item_name);
			holder.textName.setText(items.get(position));
		}else if(typeOfScreen.equalsIgnoreCase("Area")){
			holder.textPosition = (TextView) convertView.findViewById(R.id.text_item_position);
		}
		
		return convertView;
	}

	 private class ViewHolder {

		TextView textName;
		TextView textPosition;
		ImageView image;
	}

}
