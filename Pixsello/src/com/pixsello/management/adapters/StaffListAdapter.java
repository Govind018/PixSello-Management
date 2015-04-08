package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.training.StaffDetails;

public class StaffListAdapter extends ArrayAdapter<StaffDetails> {

	ArrayList<StaffDetails> details;
	
	Context thisContext;
	
	LayoutInflater infalter;
	
	int inflatableRes = 0;
	
	StaffDetails contact;
	
	public StaffListAdapter(Context context, int resource,
			ArrayList<StaffDetails> data) {
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

			holder.textTrainer = (TextView) convertView.findViewById(R.id.item_trainer);
			holder.textTrainee = (TextView) convertView.findViewById(R.id.item_trainee);
			holder.textDateAndTime = (TextView) convertView.findViewById(R.id.item_date_time);
			holder.textOther = (TextView) convertView.findViewById(R.id.item_training);
			holder.textType = (TextView) convertView.findViewById(R.id.item_training_type);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		contact = details.get(position);
		
		holder.textTrainer.setText(contact.getTrainerName());
		holder.textTrainee.setText(contact.getTraineeName());
		holder.textDateAndTime.setText(contact.getDate() + "  " + contact.getTime());
		holder.textOther.setText(contact.getOther());
		holder.textType.setText(contact.getType());
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView textTrainer;
		TextView textTrainee;
		TextView textDateAndTime;
		TextView textOther;
		TextView textType;
	}
}
