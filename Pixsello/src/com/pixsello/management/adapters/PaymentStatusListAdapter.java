package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;

public class PaymentStatusListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;
	
	Context thisContext;
	
	LayoutInflater infalter;
	
	int inflatableRes = 0;
	
	Entity guestDetail;
	
	public PaymentStatusListAdapter(Context context, int resource,
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

			holder.textService = (TextView) convertView.findViewById(R.id.text_service);
			holder.textIdentity= (TextView) convertView.findViewById(R.id.text_identity);
			holder.textType = (TextView) convertView.findViewById(R.id.text_type);
			holder.textAmount = (TextView) convertView.findViewById(R.id.text_amount);
			holder.textDueDate = (TextView) convertView.findViewById(R.id.text_due_date);
			holder.textStatus = (TextView) convertView.findViewById(R.id.text_status);
			holder.btnPay = (Button) convertView.findViewById(R.id.btn_pay);
			
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		guestDetail = details.get(position);
		
		holder.textService.setText(guestDetail.getDate());
		holder.textIdentity.setText(guestDetail.getTime());
		holder.textType.setText(guestDetail.getGuestName());
		holder.textAmount.setText(guestDetail.getCompanyName());
		holder.textDueDate.setText(guestDetail.getGender());
		holder.textStatus.setText(guestDetail.getOutTime());
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView textService;
		TextView textIdentity;
		TextView textType;
		TextView textAmount;
		TextView textDueDate;
		TextView textStatus;
		Button btnPay;
	}
}
