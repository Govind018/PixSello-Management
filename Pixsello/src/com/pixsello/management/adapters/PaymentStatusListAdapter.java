package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;
import com.pixsello.management.util.Uttilities;
import com.spundhan.pixsello.payment.PaymentActivity;

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
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
		
		holder.textService.setText(guestDetail.getServiceName());
		holder.textIdentity.setText(guestDetail.getIdentity());
		holder.textType.setText(guestDetail.getType());
		holder.textAmount.setText(guestDetail.getAmount());
		holder.textDueDate.setText(guestDetail.getDueDate());
		
		if(guestDetail.getStatus().equalsIgnoreCase("0")){
			holder.textStatus.setText("OPEN");	
			holder.btnPay.setVisibility(View.VISIBLE);
		}else{
			holder.textStatus.setText("PAID");
			holder.btnPay.setVisibility(View.INVISIBLE);
		}
		
		holder.btnPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Entity en = details.get(position);
				
				Uttilities.showToast(thisContext, en.getServiceName());

				Intent intent = new Intent(thisContext, PaymentActivity.class);
				intent.putExtra("ServiceID",en.getServiceId());
				intent.putExtra("IdentityID",en.getServiceId());
				intent.putExtra("ServiceName", en.getServiceName());
				intent.putExtra("IdentityName", en.getIdentity());
				intent.putExtra("BillNo", en.getBillNum());
				intent.putExtra("BillDate", en.getBillDate());
				intent.putExtra("Billduedate", en.getDueDate());
				intent.putExtra("Amount", en.getAmount());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				thisContext.startActivity(intent);
				
			}
		});
		
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
