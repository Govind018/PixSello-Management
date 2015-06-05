package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixsello.management.ImagePreviewActivity;
import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;

public class PastVisitorsListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity guestDetail;

	public PastVisitorsListAdapter(Context context, int resource,
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textDate = (TextView) convertView
					.findViewById(R.id.guest_date);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.guest_time);
			holder.textGuestName = (TextView) convertView
					.findViewById(R.id.guest_name);
			holder.textCompany = (TextView) convertView
					.findViewById(R.id.guest_company);
			holder.textGender = (TextView) convertView
					.findViewById(R.id.guest_gender);
			holder.textPhoto = (ImageView) convertView
					.findViewById(R.id.guest_photo);
			holder.textInTime = (TextView) convertView
					.findViewById(R.id.guest_in_time);
			holder.textOutTime = (TextView) convertView
					.findViewById(R.id.guest_out_time);
			holder.rowLayout = (LinearLayout) convertView
					.findViewById(R.id.visitors_row);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		guestDetail = details.get(position);

		if (position % 2 == 0) {
			holder.rowLayout.setBackgroundColor(thisContext.getResources()
					.getColor(R.color.items_row1));
		} else {
			holder.rowLayout.setBackgroundColor(thisContext.getResources()
					.getColor(R.color.items_row2));
		}

		holder.textDate.setText(guestDetail.getDate());
		holder.textTime.setText(guestDetail.getTime());
		holder.textGuestName.setText(guestDetail.getGuestName());
		holder.textCompany.setText(guestDetail.getCompanyName());
		holder.textGender.setText(guestDetail.getGender());
		holder.textInTime.setText(guestDetail.getInTime());
		holder.textOutTime.setText(guestDetail.getOutTime());

		if (guestDetail.getPhoto() != null) {
			byte[] imageAsBytes = Base64.decode(guestDetail.getPhoto()
					.getBytes(), Base64.DEFAULT);
			holder.textPhoto.setImageBitmap(BitmapFactory.decodeByteArray(
					imageAsBytes, 0, imageAsBytes.length));
		}

		holder.textPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Entity itemPhoto = details.get(position);

				byte[] imageAsBytes = Base64.decode(itemPhoto.getPhoto()
						.getBytes(), Base64.DEFAULT);

				Intent intent = new Intent(thisContext,
						ImagePreviewActivity.class);
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
		TextView textGuestName;
		TextView textCompany;
		TextView textGender;
		ImageView textPhoto;
		TextView textInTime;
		TextView textOutTime;
		LinearLayout rowLayout;
	}
}
