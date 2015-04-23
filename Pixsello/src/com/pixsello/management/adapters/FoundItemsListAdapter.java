package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixsello.management.ImagePreviewActivity;
import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;

public class FoundItemsListAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	Entity item;

	ArrayList<Entity> items;

	int inflatableRes = 0;

	LayoutInflater inflater;

	public FoundItemsListAdapter(Context context, int resource,
			ArrayList<Entity> objects) {
		super(context, resource, objects);

		thisContext = context;
		inflatableRes = resource;
		items = objects;
		inflater = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textDate = (TextView) convertView
					.findViewById(R.id.found_item_date);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.found_item_time);
			holder.textDescription = (TextView) convertView
					.findViewById(R.id.found_item_description);
			holder.textLocation = (TextView) convertView
					.findViewById(R.id.found_item_location);
			holder.textStaff = (TextView) convertView
					.findViewById(R.id.found_item_staff);
			holder.textFromDate = (TextView) convertView
					.findViewById(R.id.found_item_from);
			holder.textToDate = (TextView) convertView
					.findViewById(R.id.found_item_to);
			holder.image = (ImageView) convertView
					.findViewById(R.id.found_item_photo);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		item = items.get(position);

		holder.textDate.setText(item.getDate());
		holder.textTime.setText(item.getTime());
		holder.textDescription.setText(item.getDescription());
		holder.textLocation.setText(item.getLocation());
		holder.textStaff.setText(item.getStaffName());
		holder.textFromDate.setText(item.getStayDateFrom());
		holder.textToDate.setText(item.getStayDateTo());

		if (item.getPhoto() != null) {
			byte[] imageAsBytes = Base64.decode(item.getPhoto().getBytes(),
					Base64.DEFAULT);
			holder.image.setImageBitmap(BitmapFactory.decodeByteArray(
					imageAsBytes, 0, imageAsBytes.length));
		}
		
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Entity itemPhoto = items.get(position);
				
				byte[] imageAsBytes = Base64.decode(itemPhoto.getPhoto().getBytes(),
						Base64.DEFAULT);
				
				Intent intent = new  Intent(thisContext,ImagePreviewActivity.class);
				intent.putExtra("image", imageAsBytes);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				thisContext.startActivity(intent);
				
			}
		});

		return convertView;
	}

	public class ViewHolder {

		TextView textDate;
		TextView textTime;
		TextView textDescription;
		TextView textLocation;
		TextView textStaff;
		TextView textFromDate;
		TextView textToDate;
		ImageView image;

	}
}
