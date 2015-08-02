package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.guest.Entity;
import com.spundhan.pixsello.payment.PaymentActivity;

public class NewPaymentListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity guestDetail;

	public NewPaymentListAdapter(Context context, int resource,
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

				holder.textService = (TextView) convertView
						.findViewById(R.id.text_service_name);
//				holder.textIdentity = (TextView) convertView
//						.findViewById(R.id.text_identity);
				holder.textPaymentType = (TextView) convertView
						.findViewById(R.id.text_payment_type);
				holder.textAmount = (TextView) convertView
						.findViewById(R.id.text_amount);
				holder.textDueDate = (TextView) convertView
						.findViewById(R.id.text_due_date);
				holder.textStatus = (TextView) convertView
						.findViewById(R.id.text_status);
				holder.btnPay = (Button) convertView.findViewById(R.id.btn_pay);
				holder.rowLayout = (LinearLayout) convertView
						.findViewById(R.id.payment_row);

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

			holder.textService.setText(guestDetail.getServiceName());
//			holder.textIdentity.setText(guestDetail.getIdentity());
			holder.textAmount.setText(guestDetail.getAmount());
			holder.textDueDate.setText(guestDetail.getDueDate());
			holder.textPaymentType.setText(guestDetail.getType());
			holder.textStatus.setText(guestDetail.getStatus());

			if (guestDetail.getStatus().equalsIgnoreCase("0")) {
				holder.textStatus.setText("OPEN");
				holder.btnPay.setVisibility(View.VISIBLE);
			} else {
				holder.textStatus.setText("PAID");
				holder.btnPay.setVisibility(View.INVISIBLE);
			}

			holder.btnPay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Entity en = details.get(position);
					Intent intent = new Intent(thisContext,
							PaymentActivity.class);
					intent.putExtra("ServiceID", en.getServiceId());
					intent.putExtra("Identity", en.getIdentityID());
					intent.putExtra("ServiceName", en.getServiceName());
					intent.putExtra("IdentityName", en.getIdentity());
					intent.putExtra("BillNo", en.getBillNum());
					intent.putExtra("BillDate", en.getBillDate());
					intent.putExtra("Billduedate", en.getDueDate());
					intent.putExtra("Amount", en.getAmount());
					intent.putExtra("type", "old");
					intent.putExtra("url",
							"http://pixsello.in/qualitymaintenanceapp/index.php/webapp/BillPay");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					thisContext.startActivity(intent);

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	public class ViewHolder {

		TextView textService;
//		TextView textIdentity;
		TextView textAmount;
		TextView textDueDate;
		TextView textStatus;
		TextView textPaymentType;
		Button btnPay;
		LinearLayout rowLayout;
	}
}
