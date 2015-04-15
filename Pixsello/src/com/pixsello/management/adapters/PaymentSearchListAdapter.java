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

public class PaymentSearchListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;
	
	Context thisContext;
	
	LayoutInflater infalter;
	
	int inflatableRes = 0;
	
	Entity guestDetail;
	
	public PaymentSearchListAdapter(Context context, int resource,
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
			holder.textStatus1 = (TextView) convertView.findViewById(R.id.text_status1);
//			holder.btnPay = (Button) convertView.findViewById(R.id.btn_pay);
			
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		guestDetail = details.get(position);
		
		holder.textService.setText(guestDetail.getBillNum());
		holder.textIdentity.setText(guestDetail.getBillDate());
		holder.textType.setText(guestDetail.getServiceName());
		holder.textAmount.setText(guestDetail.getIdentity());
		holder.textDueDate.setText(guestDetail.getAmount());
		holder.textStatus.setText(guestDetail.getDueDate());
		holder.textStatus1.setText(guestDetail.getStatus());
		
		if(guestDetail.getStatus().equalsIgnoreCase("0")){
			holder.textStatus1.setText("OPEN");	
//			holder.btnPay.setVisibility(View.VISIBLE);
		}else{
			holder.textStatus1.setText("PAID");
//			holder.btnPay.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView textService;
		TextView textIdentity;
		TextView textType;
		TextView textAmount;
		TextView textDueDate;
		TextView textStatus;
		TextView textStatus1;
		Button btnPay;
	}
}
