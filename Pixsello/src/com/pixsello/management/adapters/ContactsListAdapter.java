package com.pixsello.management.adapters;

import java.util.ArrayList;

import com.pixsello.management.R;
import com.pixsello.management.contact.ContactDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

			holder.textServiceDesc = (TextView) convertView.findViewById(R.id.item_service);
			holder.textContactPerson = (TextView) convertView.findViewById(R.id.item_contact_person);
			holder.textContactNumber = (TextView) convertView.findViewById(R.id.item_contact_number);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		contact = details.get(position);
		
		holder.textServiceDesc.setText(contact.getServiceDescription());
		holder.textContactPerson.setText(contact.getContactPerson());
		holder.textContactNumber.setText(contact.getContactNumber());
		
		return convertView;
	}
	
	public class ViewHolder{
		
		TextView textServiceDesc;
		TextView textContactPerson;
		TextView textContactNumber;
	}
}
