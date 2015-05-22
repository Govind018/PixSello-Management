package com.pixsello.management.adapters;

import java.util.ArrayList;

import com.pixsello.management.R;
import com.pixsello.management.contact.ContactDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsListAdapter extends ArrayAdapter<ContactDetails> {

	ArrayList<ContactDetails> details;

	Context thisContext;

	LayoutInflater infalter;

	int inflatableRes = 0;

	ContactDetails contact;

	public ContactsListAdapter(Context context, int resource,
			ArrayList<ContactDetails> data) {
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

			holder.textServiceDesc = (TextView) convertView
					.findViewById(R.id.item_service);
			holder.textContactPerson = (TextView) convertView
					.findViewById(R.id.item_contact_person);
			holder.textContactNumber = (TextView) convertView
					.findViewById(R.id.item_contact_number);
			holder.textQuickInfo = (TextView) convertView
					.findViewById(R.id.item_contact_quickinfo);
			holder.rowLayout = (LinearLayout) convertView.findViewById(R.id.contact_row_layout);
		} else {	
			holder = (ViewHolder) convertView.getTag();
		}

		contact = details.get(position);

//		if( position%2 == 0){
//			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row1));
//		}else{
//			holder.rowLayout.setBackgroundColor(thisContext.getResources().getColor(R.color.items_row2));
//		}

		holder.textServiceDesc.setText(contact.getServiceDescription());
		holder.textContactPerson.setText(contact.getContactPerson());
		holder.textContactNumber.setText(contact.getContactNumber());

		if (contact.getTypeOfPerson().equalsIgnoreCase("1")) {
			holder.textQuickInfo.setVisibility(View.GONE);
		} else {
			holder.textQuickInfo.setVisibility(View.VISIBLE);
			holder.textQuickInfo.setText(contact.getQuickInfo());
		}

		return convertView;                
	}

	public class ViewHolder {

		TextView textServiceDesc;
		TextView textContactPerson;
		TextView textContactNumber;
		TextView textQuickInfo;
		LinearLayout rowLayout;
	}
}
