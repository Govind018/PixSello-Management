package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
			holder.ratingAmbience = (LinearLayout) convertView
					.findViewById(R.id.rating_ambience);
			holder.ratingServices = (LinearLayout) convertView
					.findViewById(R.id.rating_services);
			holder.ratingFood = (LinearLayout) convertView
					.findViewById(R.id.rating_food);
			holder.rowLayout = (LinearLayout) convertView.findViewById(R.id.feedback_row);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		entity = details.get(position);
		
		if( position%2 == 0){
			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row1));
		}else{
			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row2));
		}

		holder.textGuestName.setText(entity.getGuestName());
		holder.textCompany.setText(entity.getCompanyName());
		holder.textFeedback.setText(entity.getFeedback());

		showRatingStars(holder.ratingAmbience,
				Integer.parseInt(entity.getAmbience()));
		showRatingStars(holder.ratingServices,
				Integer.parseInt(entity.getServices()));
		showRatingStars(holder.ratingFood, Integer.parseInt(entity.getFood()));

		return convertView;
	}

	private void showRatingStars(LinearLayout ratingLayout, int number) {

		for (int i = 0; i < number; i++) {

			ImageView im = new ImageView(thisContext);
			im.setImageResource(R.drawable.star);
			im.setLayoutParams(new LayoutParams(30, 30));
			ratingLayout.addView(im);
		}
	}

	public class ViewHolder {

		TextView textGuestName;
		TextView textCompany;
		TextView textFeedback;
		TextView textAmbience;
		TextView textServices;
		TextView textFood;
		LinearLayout ratingAmbience;
		LinearLayout ratingServices;
		LinearLayout ratingFood;
		LinearLayout rowLayout;
	}
}
