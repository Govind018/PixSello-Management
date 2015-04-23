package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;

public class FeedbackListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity entity;

	public FeedbackListAdapter(Context context, int resource,
			ArrayList<Entity> data) {
		super(context, resource, data);
		thisContext = context;
		details = data;
		inflatableRes = resource;
		infalter = (LayoutInflater) thisContext
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
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textGuestName = (TextView) convertView
					.findViewById(R.id.text_guest_name);
			holder.textCompany = (TextView) convertView
					.findViewById(R.id.text_company);
			holder.textFeedback = (TextView) convertView
					.findViewById(R.id.text_feedback);
			holder.textAmbience = (TextView) convertView
					.findViewById(R.id.text_ambience);
			holder.textServices = (TextView) convertView
					.findViewById(R.id.text_services);
			holder.textFood = (TextView) convertView
					.findViewById(R.id.text_food);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		entity = details.get(position);

		holder.textGuestName.setText(entity.getGuestName());
		holder.textCompany.setText(entity.getCompanyName());
		holder.textFeedback.setText(entity.getFeedback());
		holder.textAmbience.setText(entity.getAmbience());
		holder.textServices.setText(entity.getServices());
		holder.textFood.setText(entity.getFood());

		return convertView;
	}

	public class ViewHolder {

		TextView textGuestName;
		TextView textCompany;
		TextView textFeedback;
		TextView textAmbience;
		TextView textServices;
		TextView textFood;
	}
}
