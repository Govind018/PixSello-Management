package com.pixsello.management.adapters;

import java.util.ArrayList;

import com.pixsello.management.R;
import com.pixsello.management.contact.ContactDetails;
import com.pixsello.management.guest.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionTakenListAdapter extends ArrayAdapter<Entity> {

	ArrayList<Entity> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	Entity item;

	public ActionTakenListAdapter(Context context, int resource,
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = infalter.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textActionTakenDate = (TextView) convertView
					.findViewById(R.id.item_action_date);
			holder.textActionTakenTime = (TextView) convertView
					.findViewById(R.id.item_action_time);
			holder.textActionTaken = (TextView) convertView
					.findViewById(R.id.item_action_taken);
			holder.rowLayout = (LinearLayout) convertView.findViewById(R.id.action_row_layout);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		item = details.get(position);

		if (position % 2 == 0) {
			holder.rowLayout.setBackgroundColor(thisContext.getResources()
					.getColor(R.color.items_row1));
		} else {
			holder.rowLayout.setBackgroundColor(thisContext.getResources()
					.getColor(R.color.items_row2));
		}

		holder.textActionTakenDate.setText(item.getDate());
		holder.textActionTakenTime.setText(item.getTime());
		holder.textActionTaken.setText(item.getActionTaken());

		return convertView;
	}

	public class ViewHolder {

		TextView textActionTakenDate;
		TextView textActionTakenTime;
		TextView textActionTaken;
		LinearLayout rowLayout;
	}
}
