package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		 infalter = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return details.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textDate = (TextView) convertView.findViewById(R.id.guest_date);
			holder.textTime= (TextView) convertView.findViewById(R.id.guest_time);
			holder.textGuestName = (TextView) convertView.findViewById(R.id.guest_name);
			holder.textCompany = (TextView) convertView.findViewById(R.id.guest_company);
			holder.textGender = (TextView) convertView.findViewById(R.id.guest_gender);
			holder.textPhoto = (ImageView) convertView.findViewById(R.id.guest_photo);
			holder.textInTime = (TextView) convertView.findViewById(R.id.guest_in_time);
			holder.textOutTime = (TextView) convertView.findViewById(R.id.guest_out_time);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		guestDetail = details.get(position);
		
		holder.textDate.setText(guestDetail.getDate());
		holder.textTime.setText(guestDetail.getTime());
		holder.textGuestName.setText(guestDetail.getGuestName());
		holder.textCompany.setText(guestDetail.getCompanyName());
		holder.textGender.setText(guestDetail.getGender());
//		holder.textPhoto.setText(guestDetail.getPhoto());
		holder.textInTime.setText(guestDetail.getInTime());
		holder.textOutTime.setText(guestDetail.getOutTime());
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView textDate;
		TextView textTime;
		TextView textGuestName;
		TextView textCompany;
		TextView textGender;
		ImageView textPhoto;
		TextView textInTime;
		TextView textOutTime;
	}
}