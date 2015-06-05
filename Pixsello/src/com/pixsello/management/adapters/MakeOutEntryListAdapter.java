package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixsello.management.ImagePreviewActivity;
import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.guest.MakeOutEntryActivity;

public class MakeOutEntryListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity guestDetail;

	public MakeOutEntryListAdapter(Context context, int resource,
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

		try {

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
//				holder.textInTime = (TextView) convertView
//						.findViewById(R.id.guest_in_time);
				holder.textOutTime = (Button) convertView
						.findViewById(R.id.guest_out_time);
				holder.textVisitorName = (TextView) convertView
						.findViewById(R.id.guest_company_visitor);
				holder.image = (ImageView) convertView
						.findViewById(R.id.guest_photo);
				holder.rowLayout = (LinearLayout) convertView.findViewById(R.id.out_entry_row);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			guestDetail = details.get(position);

			if( position%2 == 0){
				holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row1));
			}else{
				holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row2));
			}
			
			holder.textDate.setText(guestDetail.getDate());
			holder.textTime.setText(guestDetail.getTime());
			holder.textGuestName.setText(guestDetail.getGuestName());
			holder.textCompany.setText(guestDetail.getCompanyName());
			holder.textGender.setText(guestDetail.getGender());
			holder.textVisitorName.setText(guestDetail.getVisitorName());
//			holder.textInTime.setText(guestDetail.getInTime());

			byte[] imageAsBytes = Base64.decode(guestDetail.getPhoto()
					.getBytes(), Base64.DEFAULT);
			holder.image.setImageBitmap(BitmapFactory.decodeByteArray(
					imageAsBytes, 0, imageAsBytes.length));

//			if (guestDetail.getOutTime().equalsIgnoreCase("0")) {
//				holder.textOutTime.setText("UPDATE");
//			} else {
//				holder.textOutTime.setText(guestDetail.getOutTime());
//			}

			holder.textOutTime.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Entity en = details.get(position);
					if (en.getOutTime().equalsIgnoreCase("0")) {

						MakeOutEntryActivity ac = new MakeOutEntryActivity();
						ac.updateOutTime(en.getItemID());
					}
				}
			});

			holder.image.setOnClickListener(new OnClickListener() {

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

		} catch (Exception e) {
			System.out.println(e);

		}

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
		Button textOutTime;
		TextView textVisitorName;
		ImageView image;
		LinearLayout rowLayout;
	}

	public void showDailog() {

		// showDialog(TIME_DIALOG_ID);

		Dialog d = new Dialog(thisContext);
		d.setTitle("fsdf");
		d.show();

	}
}
