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

public class SearchListAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ArrayList<Entity> items;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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

		imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.image);

		thumbNail.setImageUrl(entity.getImageUrl(), imageLoader);

		holder.textName = (TextView) convertView
				.findViewById(R.id.text_user_name);
		holder.textPost = (TextView) convertView
				.findViewById(R.id.text_user_post);
		holder.textName.setText(entity.getName());
		holder.textPost.setText(entity.getPost());

		return convertView;
	}

	private class ViewHolder {

		TextView textName;
		TextView textPost;
		ImageView image;
	}
}
