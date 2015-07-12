package com.belgaum.events.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.belgaum.events.R;
import com.belgaum.events.util.Entity;

public class SearchListAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ArrayList<Entity> items;

	public SearchListAdapter(Context context, int resource,
			ArrayList<Entity> objects) {
		super(context, resource, objects);
		thisContext = context;
		inflatableRes = resource;
		items = objects;
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
		
		holder.image = (ImageView) convertView.findViewById(R.id.image);
		holder.textName = (TextView) convertView
				.findViewById(R.id.text_user_name);
		holder.textName.setText(entity.getName());
		
//		byte[] imageAsBytes = Base64.decode(entity.getImage(),Base64.DEFAULT);
//		Bitmap m = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//		holder.image.setImageBitmap(m);

		return convertView;
	}

	private class ViewHolder {

		TextView textName;
		ImageView image;
	}
}
