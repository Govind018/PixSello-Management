package com.belgaum.events.adapter;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.belgaum.events.AppController;
import com.belgaum.events.R;
import com.belgaum.events.util.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventDetailAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	LayoutInflater inflater;

	int inflatableRes = 0;

	ArrayList<Entity> details;
	
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public EventDetailAdapter(Context context, int resource,
			ArrayList<Entity> objects) {
		super(context, resource, objects);
		thisContext = context;
		details = objects;
		inflatableRes = resource;
		inflater = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return details.size();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		Entity en = details.get(position);
		if (en.isHeader()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		holder = new ViewHolder();

		int type = getItemViewType(position);
		if (type == 0) {
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.list_item_section, null);
				holder.textEventName = (TextView) convertView
						.findViewById(R.id.event_name);
				holder.textEventDesc = (TextView) convertView
						.findViewById(R.id.event_desc);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			Entity entity = details.get(position);
			imageLoader = AppController.getInstance().getImageLoader();
			NetworkImageView thumbNail = (NetworkImageView) convertView
					.findViewById(R.id.image);
			thumbNail.setImageUrl(entity.getImageUrl(), imageLoader);
			holder.textEventName.setText(entity.getEventName());
			holder.textEventDesc.setText(entity.getEventDescription());
			
		} else {
			if (convertView == null) {
				convertView = inflater.inflate(inflatableRes, null);
				holder.textSenderName = (TextView) convertView
						.findViewById(R.id.text_sender_name);
				holder.textSendMessage = (TextView) convertView
						.findViewById(R.id.text_sender_message);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Entity entity = details.get(position);
			holder.textSenderName.setText(entity.getName());
			holder.textSendMessage.setText(entity.getMessage());
		}

		return convertView;
	}

	private class ViewHolder {

		TextView textSenderName;
		TextView textSendMessage;
		RelativeLayout sentMsg;
		TextView textEventName;
		TextView textEventDesc;

	}
}
