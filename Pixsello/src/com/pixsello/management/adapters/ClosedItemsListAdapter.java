package com.pixsello.management.adapters;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixsello.management.R;
import com.pixsello.management.action.ClosedItemsDialog;
import com.pixsello.management.guest.Entity;

public class ClosedItemsListAdapter extends ArrayAdapter<Entity> {

	Context thisContext;

	Entity item;

	ArrayList<Entity> items;

	int inflatableRes = 0;

	LayoutInflater inflater;
	FragmentManager thisManager;

	public ClosedItemsListAdapter(Context context,FragmentManager manager, int resource,
			ArrayList<Entity> objects) {
		super(context, resource, objects);

		thisContext = context;
		inflatableRes = resource;
		items = objects;
		thisManager = manager;
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
					.findViewById(R.id.closed_item_date);
			holder.textTime = (TextView) convertView
					.findViewById(R.id.closed_item_time);
			holder.textDescription = (TextView) convertView
					.findViewById(R.id.closed_item_description);
			holder.textLocation = (TextView) convertView
					.findViewById(R.id.closed_item_where);
			holder.textReported = (TextView) convertView
					.findViewById(R.id.closed_item_reported);
			holder.textRespo = (TextView) convertView
					.findViewById(R.id.closed_item_respo);
			holder.textActionTaken = (TextView) convertView
					.findViewById(R.id.closed_item_action_taken);
			holder.textView = (TextView) convertView.findViewById(R.id.lbl_view);
			holder.rowLayout = (LinearLayout) convertView.findViewById(R.id.closed_items_row);
                
		} else {

			holder = (ViewHolder) convertView.getTag();
  
		}

		item = items.get(position);

		if( position%2 == 0){
			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row1));
		}else{
			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row2));
		}
		
		holder.textDate.setText(item.getDate());
		holder.textTime.setText(item.getTime());
		holder.textDescription.setText(item.getDescription());
		holder.textLocation.setText(item.getLocation());
		holder.textReported.setText(item.getReported());
		holder.textRespo.setText(item.getResponsibility());
		holder.textActionTaken.setText(item.getActionTaken());
		
		holder.textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Entity en = items.get(position);
				Bundle bundle = new Bundle();
				bundle.putString("id", en.getItemID());
				
				ClosedItemsDialog dialog = new ClosedItemsDialog();
				dialog.setArguments(bundle);
				dialog.show(thisManager, "");
			}
		});
		
		return convertView;
	}

	public class ViewHolder {

		TextView textDate;
		TextView textTime;
		TextView textDescription;
		TextView textLocation;
		TextView textReported;
		TextView textRespo;
		TextView textActionTaken;
		LinearLayout rowLayout;
		TextView textView;
	}
	
	public void showDailog(){
		
		
		Dialog dailog = new Dialog(thisContext);
		dailog.setTitle("");
		dailog.show();
	}
}
