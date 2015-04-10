package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pixsello.management.R;
import com.pixsello.management.action.ActionItem;
import com.pixsello.management.action.ActiveItemsListActivity;

public class ActiveItemsListAdapter extends ArrayAdapter<ActionItem> {

	Context thisContext;

	ActionItem item;

	ArrayList<ActionItem> items;

	int inflatableRes = 0;

	LayoutInflater inflater;

	public ActiveItemsListAdapter(Context context, int resource,
			ArrayList<ActionItem> objects) {
		super(context, resource, objects);

		thisContext = context;
		inflatableRes = resource;
		items = objects;
		inflater = (LayoutInflater) thisContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {

			holder = new ViewHolder();
			convertView = inflater.inflate(inflatableRes, null);
			convertView.setTag(holder);

			holder.textDate = (TextView) convertView
					.findViewById(R.id.active_item_date);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.active_item_time);
			holder.textDispersion = (TextView) convertView
					.findViewById(R.id.active_item_dispersion);
			holder.textLocation = (TextView) convertView
					.findViewById(R.id.active_item_where);
			holder.textReported = (TextView) convertView
					.findViewById(R.id.active_item_reported);
			holder.textRespo = (TextView) convertView
					.findViewById(R.id.active_item_respo);
			holder.textActionTaken = (TextView) convertView
					.findViewById(R.id.active_item_action_taken);
			holder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);

		} else {

			holder = (ViewHolder) convertView.getTag();
  
		}

		item = items.get(position);

		holder.textDate.setText(item.getDate());
		holder.textTime.setText(item.getTime());
		holder.textDispersion.setText(item.getDispersion());
		holder.textLocation.setText(item.getLocation());
		holder.textReported.setText(item.getReported());
		holder.textRespo.setText(item.getResponsibility());
		holder.textActionTaken.setText(item.getActionTaken());
		
		holder.btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ActionItem item1 = items.get(position);
//				ActiveItemsListActivity.showDailog(item1.getItemID());
				
				ActiveItemsListActivity a = new ActiveItemsListActivity();
				a.showDailog(item1.getItemID());
			}
		});

		return convertView;
	}

	public class ViewHolder {

		TextView textDate;
		TextView textTime;
		TextView textDispersion;
		TextView textLocation;
		TextView textReported;
		TextView textRespo;
		TextView textActionTaken;
		Button btnUpdate;

	}
	
	public void showDailog(){
		
		
		Dialog dailog = new Dialog(thisContext);
		dailog.setTitle("");
		dailog.show();
	}
}