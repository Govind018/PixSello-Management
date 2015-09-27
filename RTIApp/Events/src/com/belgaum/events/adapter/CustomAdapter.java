package com.belgaum.events.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.belgaum.events.AppController;
import com.belgaum.events.R;
import com.belgaum.events.util.Entity;

public class CustomAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ArrayList<Entity> items;

	String typeOfScreen;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomAdapter(Context context, int resource,
			ArrayList<Entity> objects, String type) {
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

		Entity entity = items.get(position);

		imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.image);

		thumbNail.setImageUrl(entity.getImageUrl(), imageLoader);

		if (typeOfScreen.equalsIgnoreCase("Events")) {
			holder.textEventDate = (TextView) convertView.findViewById(R.id.text_item_date);
			holder.textEventDate.setVisibility(View.VISIBLE);
			holder.textName = (TextView) convertView
					.findViewById(R.id.text_item_name);
			holder.textName.setText(entity.getName());
			holder.textEventDate.setText(entity.getEventDate());
		} else if (typeOfScreen.equalsIgnoreCase("Area")) {
			holder.textPosition = (TextView) convertView
					.findViewById(R.id.text_item_position);
			holder.textName = (TextView) convertView
					.findViewById(R.id.text_item_name);
			holder.textName.setText(entity.getName());
			holder.textPosition.setText(entity.getPost());
//			holder.textEventDate.setVisibility(View.GONE);
		} else {
			holder.textPosition = (TextView) convertView
					.findViewById(R.id.text_item_position);
			holder.textName = (TextView) convertView
					.findViewById(R.id.text_item_name);
			holder.textName.setText(entity.getName());
			holder.textPosition.setText(entity.getPost());
//			holder.textEventDate.setVisibility(View.GONE);
		}

		return convertView;
	}

	private class ViewHolder {

		TextView textName;
		TextView textPosition;
		ImageView image;
		TextView textEventDate;
	}
}
