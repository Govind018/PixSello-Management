package com.belgaum.events.adapter;

import java.util.ArrayList;

import com.belgaum.events.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//Not using
public class EventDetailAdapter extends ArrayAdapter<String> {

	Context thisContext;

	LayoutInflater inflater;

	int inflatableRes = 0;

	ArrayList<String> details;

	public EventDetailAdapter(Context context, int resource,
			ArrayList<String> objects) {
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textSenderName = (TextView) convertView
					.findViewById(R.id.text_header);
			holder.textSendMessage = (TextView) convertView
					.findViewById(R.id.text_content);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		

		return convertView;
	}

	private class ViewHolder {

		TextView textSenderName;
		TextView textSendMessage;

	}
}
