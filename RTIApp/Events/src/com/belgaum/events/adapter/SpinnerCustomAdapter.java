package com.belgaum.events.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.belgaum.events.R;
import com.belgaum.events.util.Entity;

public class SpinnerCustomAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ArrayList<Entity> items;

	String spinnerType;

	Entity entity;

	public SpinnerCustomAdapter(Context context, int resource,
			ArrayList<Entity> objects, String type) {
		super(context, resource, objects);
		thisContext = context;
		inflatableRes = resource;
		items = objects;
		spinnerType = type;
		infalter = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		holder.textName = (TextView) convertView.findViewById(R.id.spinner_item_text);

		entity = items.get(position);

		if (spinnerType.equalsIgnoreCase("Prefix")) {
			holder.textName.setText(entity.getPrefixName());
		} else if (spinnerType.equalsIgnoreCase("Table")) {
			holder.textName.setText(entity.getTableName());
		}

		return convertView;

	}

	private class ViewHolder {

		TextView textName;
	}
}
