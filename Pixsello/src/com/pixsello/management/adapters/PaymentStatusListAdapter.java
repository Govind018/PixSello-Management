package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
						.findViewById(R.id.text_serivce_name);
//				holder.textIdentity = (TextView) convertView
//						.findViewById(R.id.text_identity);
				holder.textBillDate = (TextView) convertView
						.findViewById(R.id.text_bill_date);
				holder.textAmount = (TextView) convertView
						.findViewById(R.id.text_amount);
				holder.textDueDate = (TextView) convertView
						.findViewById(R.id.text_due_date);
				holder.textStatus = (TextView) convertView
						.findViewById(R.id.text_status);
				holder.rowLayout = (LinearLayout) convertView
						.findViewById(R.id.payment_row);
				holder.textBillNumber = (TextView) convertView
						.findViewById(R.id.text_bill_number);

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

			holder.textBillNumber.setText(guestDetail.getBillNum());
			holder.textService.setText(guestDetail.getServiceName());
//			holder.textIdentity.setText(guestDetail.getIdentity());
			holder.textBillDate.setText(guestDetail.getBillDate());
			holder.textAmount.setText(guestDetail.getAmount());
			holder.textDueDate.setText(guestDetail.getDueDate());

			if (guestDetail.getStatus().equalsIgnoreCase("0")) {
				holder.textStatus.setText("OPEN");
			} else {
				holder.textStatus.setText("PAID");
			}

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
		TextView textBillDate;
		TextView textBillNumber;
		LinearLayout rowLayout;
	}
}
